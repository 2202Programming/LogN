package PID;

import java.util.ArrayList;
import java.util.Random;

import PID.tester.AutoPIDTesterWindow;
import comms.FileLoader;

/**
 * A class used for automatically tuning PID loops using an evolutionary
 * strategy with semi-intelligent improvement, once some improvement has been
 * made.
 * 
 * @author SecondThread
 */
public class AutoPIDTuner {

	/**
	 * The tunable thing that is suppose to be tuned
	 */
	private AutoPIDTunable toTune;

	/**
	 * The previous best PID values and the PID values that will be tested
	 */
	private PIDValues bestPIDValues=new PIDValues(.4, .03, 0), testingPIDValues=new PIDValues(.4, .03, 0);

	/**
	 * The PIDController that is used by the Tunable object. This is needed so
	 * that new PID values can be set when they are tested, and so that error
	 * can be reset after tests.
	 */
	private PIDController pidController=new PIDController(testingPIDValues);

	/**
	 * The error tolerance. The loop will count as being complete when the error
	 * is less than this for maxErrorSafeCounter frames
	 */
	private double minError=0.01;

	/**
	 * The counter and required frames counting how long the tunable has been
	 * less than <i>minError</i> from its target value.
	 */
	private int errorSafeCounter=0, maxErrorSafeCounter=40;

	/**
	 * The number of frames that this loop has used, and the number of frames
	 * that can be used before these PID values are marked unsuccessful.
	 */
	private int currentTuneCounter=0, maxTuneCounter=1200;

	/**
	 * The number of frames the best PID values took to run
	 */
	private int bestTuneTime=maxTuneCounter+1;

	/**
	 * The number the last run (not necessarily the best) took to run, which is
	 * displayed in the graph, if it is being used.
	 */
	private int lastTuneCounter=0;

	/**
	 * The change in kp, ki, and kd to make the last random PID values, which
	 * will continue to be used if they make things better.
	 */
	private double dp=0, di=0, dd=0;

	/**
	 * The number of PID values tried, and the max number to be tried,
	 * respectively.
	 */
	private int timesTried=0, maxTries=100;

	private int ceterusPluribusCounter=0;

	/**
	 * An array list of strings that will be logged after <i>maxTries</i>
	 * trials.
	 */
	private ArrayList<String> toWrite=new ArrayList<String>();

	/**
	 * The constructor for AutoPIDTuner, which passes in what needs to be tuned.
	 * 
	 * @param toTune
	 */
	public AutoPIDTuner(AutoPIDTunable toTune) {
		this.toTune=toTune;
	}

	/**
	 * This needs to be called once a frame or update cycle or everything will
	 * break.
	 */
	public void update() {
		tunePID();
	}

	/**
	 * <h1>Evolutionary PID Tuning Pro Strat:</h1> <br>
	 * Start with <.1, 0, 0>, or whatever it says at the top<br>
	 * Test and record time <br>
	 * <br>
	 * Change one number a little bit.<br>
	 * Test and record the new time <br>
	 * <br>
	 * If the new one is better, start from there and keep doing the same thing,
	 * <br>
	 * otherwise, try another random mutation <br>
	 */
	private void tunePID() {
		if (doneTesting()) return;
		if (!toTune.getResetFinished()) return;

		currentTuneCounter++;
		checkError();
		if (errorSafeCounter>=maxErrorSafeCounter||currentTuneCounter>=maxTuneCounter) {
			if (shouldStartRandomTest()) {
				startRandomTest();
			}
			else {
				accountForLastTest();
				startNewTest();
			}
			return;
		}

		sendOutputToTunable();
	}

	/**
	 * Records the old PID values with their results both in the <i>toWrite</i>
	 * and sends them to the AutoPIDTesterWindow, if its main is currently
	 * running.
	 */
	private void recordValuesToLog() {
		toWrite.add(timesTried+","+testingPIDValues.kp+","+testingPIDValues.ki+","+testingPIDValues.kp+","
				+currentTuneCounter+",14,"+(currentTuneCounter<bestTuneTime?1:0));
		if (AutoPIDTesterWindow.shouldSetValues) {
			AutoPIDTesterWindow.window.setInfo(testingPIDValues+"", bestPIDValues+"", timesTried, bestTuneTime+"",
					lastTuneCounter);
		}
	}

	/**
	 * Adjusts errorSafeCounter by incrementing it if the tunable's error is
	 * within the min error range, or setting it to zero if it isn't
	 */
	private void checkError() {
		if (Math.abs(toTune.getError())<minError) {
			errorSafeCounter++;
		}
		else {
			errorSafeCounter=0;
		}
	}

	/**
	 * Makes a slight variant of the current PID values which will be tried next
	 * in evolutionary tuning.<br>
	 * It also stores the change made to kp, ki, and kd in dp, di, and dd
	 * 
	 * @param lastValues
	 *            The PIDValues that the variant will be based on.
	 * @return A variant of <i>lastValues</i> with a slight random change to
	 *         each constant
	 */
	private PIDValues getVariant(PIDValues lastValues) {
		Random r=new Random();

		if (r.nextInt(3)==0) {
			return getCeterusParibusVarient(lastValues);
		}

		double divider=1;// Math.log(timesTried+2);
		dp=Math.pow(r.nextDouble()-0.5, 3)*4/divider;
		di=Math.pow(r.nextDouble()-0.5, 3)/15/divider;
		dd=Math.pow(r.nextDouble()-0.5, 3)/3/divider;

		return new PIDValues(Math.max(lastValues.kp+dp, 0), Math.max(lastValues.ki+di, 0),
				Math.max(lastValues.kd+dd, 0));
	}

	private PIDValues getCeterusParibusVarient(PIDValues lastValues) {
		ceterusPluribusCounter++;
		dp=di=dd=0;
		switch (ceterusPluribusCounter) {
		case 0:
			dp=0.05;
			break;
		case 1:
			di=0.015;
			break;
		case 2:
			dd=0.025;
			break;
		case 3:
			dp=-0.05;
			break;
		case 4:
			di=-0.015;
			break;
		case 5:
			dd=-0.025;
			break;
		default:
			ceterusPluribusCounter=-1;
			return getCeterusParibusVarient(lastValues);
		}

		return new PIDValues(Math.max(lastValues.kp+dp, 0), Math.max(lastValues.ki+di, 0),
				Math.max(lastValues.kd+dd, 0));
	}

	/**
	 * If the last PID values were super great, then this can be called to
	 * continue the pattern they started. This is useful because it drastically
	 * speeds up evolution once randomness is onto something.
	 * 
	 * @param lastValues
	 *            The previously used values that were good, which will be
	 *            extrapolated from
	 * @return The new PID values which are extrapolated from <i>lastValues</i>
	 */
	private PIDValues extrapolate(PIDValues lastValues) {
		return new PIDValues(Math.max(lastValues.kp+dp, 0), Math.max(lastValues.ki+di, 0),
				Math.max(lastValues.kd+dd, 0));
	}

	/**
	 * Checks to see if the maximum number of tests have been performed. <br>
	 * If they have, the data in <i>toWrite</i> is writen in the .csv file.
	 * 
	 * @return true if the maximum number of tests have been reached, false
	 *         otherwise.
	 */
	private boolean doneTesting() {
		if (timesTried>maxTries) {
			System.out.println("done.");
			String total="";
			for (String s : toWrite) {
				total+=s+"\n";
			}
			FileLoader.writeToFile("PIDLog.csv", total);
			return true;
		}
		return false;
	}

	/**
	 * Starts a new test, either extrapolating from the past successful
	 * PIDValues, or starting with new ones from the previous best PID values.
	 */
	private void startNewTest() {
		toTune.startReset();
		timesTried++;
		recordValuesToLog();
	}

	private void startRandomTest() {
		pidController.resetError();
		currentTuneCounter=0;
		pidController.setValues(bestPIDValues);
		toTune.setToRandomState();
	}

	/**
	 * Keeps track of the last test's information and resets values. It does not
	 * start a new test yet though.
	 */
	public void accountForLastTest() {
		if (currentTuneCounter<bestTuneTime) {// NEW BEST!!!
			bestTuneTime=currentTuneCounter;
			bestPIDValues=testingPIDValues;
			testingPIDValues=extrapolate(bestPIDValues);
		}
		else {
			testingPIDValues=getVariant(bestPIDValues);
		}

		pidController.setValues(testingPIDValues);
		pidController.resetError();
		lastTuneCounter=currentTuneCounter;
		currentTuneCounter=0;
	}

	/**
	 * Sends the output from <i>pidController</i> to <i>toTune</i>
	 */
	private void sendOutputToTunable() {
		double output=pidController.calculate(0, toTune.getError());
		toTune.setValue(output);
	}

	private boolean shouldStartRandomTest() {
		return AutoPIDTesterWindow.shouldSetValues&&AutoPIDTesterWindow.window.setToRandomState();
	}

}
