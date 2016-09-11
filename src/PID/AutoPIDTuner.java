package PID;

import java.util.ArrayList;
import java.util.Random;

import PID.tester.AutoPIDTesterWindow;
import comms.FileLoader;

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
	 * The PIDController that is used by the Tunable object. This is needed so that new PID values can be set when they are tested, and so that error can be reset after tests.
	 */
	private PIDController pidController=new PIDController(testingPIDValues);
	
	/**
	 * The error tolerance. The loop will count as being complete when the error is less than this for maxErrorSafeCounter frames
	 */
	private double minError=0.01;
	
	/**
	 * The counter and required frames counting how long the tunable has been less than <i>minError</i> from its target value.
	 */
	private int errorSafeCounter=0, maxErrorSafeCounter=40;
	
	/**
	 * The number of frames that this loop has used, and the number of frames that can be used before these PID values are marked unsuccessful.
	 */
	private int currentTuneCounter=0, maxTuneCounter=1200;
	
	/**
	 * The number of frames the best PID values took to run
	 */
	private int bestTuneTime=maxTuneCounter+1;
	
	/**
	 * The number the last run (not necessarily the best) took to run, which is displayed in the graph, if it is being used.
	 */
	private int lastTuneCounter=0;
	
	
	/**
	 * The change in kp, ki, and kd to make the last random PID values, which will continue to be used if they make things better.
	 */
	private double dp=0, di=0, dd=0;
	
	/**
	 * The number of PID values tried, and the max number to be tried, respectively.
	 */
	private int timesTried=0, maxTries=100;

	private ArrayList<String> toWrite=new ArrayList<String>();

	public AutoPIDTuner(AutoPIDTunable toTune) {
		this.toTune=toTune;
	}

	public void update() {
		tunePID();
	}

	/**
	 * <h1>Evolutionary PID Tuning Pro Strat:</h1> <br>
	 * <br>
	 * Start with <.1, 0, 0> <br>
	 * Test and record time <br>
	 * <br>
	 * Change one number a little bit.<br>
	 * Test and record the new time <br>
	 * <br>
	 * If the new one is better, start from there and keep doing the same thing,
	 * otherwise, try another random mutation
	 */
	private void tunePID() {
		if (timesTried>maxTries) {
			System.out.println("done.");
			String total="";
			for (String s:toWrite) {
				total+=s+"\n";
			}
			FileLoader.writeToFile("PIDLog.csv", total);
			return;
		}
		if (!toTune.getResetFinished()) {
			return;
		}
		checkForFinished();
		if (errorSafeCounter>=maxErrorSafeCounter||currentTuneCounter>=maxTuneCounter) {
			if (currentTuneCounter<bestTuneTime) {
				System.out.println("\nTested "+testingPIDValues+". "+currentTuneCounter);
				System.out.println("NEW BEST!!!");
				bestTuneTime=currentTuneCounter;
				bestPIDValues=testingPIDValues;
				testingPIDValues=extrapolate(bestPIDValues);
			}
			else {
				testingPIDValues=getVarient(bestPIDValues);
				System.out.print(" "+currentTuneCounter+" ");
			}

			pidController.setValues(testingPIDValues);
			pidController.resetError();
			lastTuneCounter=currentTuneCounter;
			currentTuneCounter=0;
			toTune.startReset();
			timesTried++;
			if (timesTried%10==0) {
				System.out.println("\n\nGeneration"+timesTried+"\n\n");
			}
			tryToSetValues();
			return;
		}
		double output=pidController.calculate(0, toTune.getError());
		toTune.setValue(output);
	}
	
	private void tryToSetValues() {
		toWrite.add(timesTried+","+testingPIDValues.kp+","+testingPIDValues.ki+","+testingPIDValues.kp+","+currentTuneCounter
				+",14,"+(currentTuneCounter<bestTuneTime?1:0));
		if (AutoPIDTesterWindow.shouldSetValues) {
			AutoPIDTesterWindow.window.setInfo(testingPIDValues+"", bestPIDValues+"", timesTried, bestTuneTime+"", lastTuneCounter);
		}
	}

	private void checkForFinished() {
		currentTuneCounter++;
		if (Math.abs(toTune.getError())<minError) {
			errorSafeCounter++;
		}
		else {
			errorSafeCounter=0;
		}
	}

	private PIDValues getVarient(PIDValues lastValues) {
		Random r=new Random();
		double divider=1;//Math.log(timesTried+2);
		dp=Math.pow(r.nextDouble()-0.5, 3)*4/divider;
		di=Math.pow(r.nextDouble()-0.5, 3)/15/divider;
		dd=Math.pow(r.nextDouble()-0.5, 3)/10/divider;

		return new PIDValues(lastValues.kp+dp, lastValues.ki+di, lastValues.kd+dd);
	}

	private PIDValues extrapolate(PIDValues lastValues) {
		return new PIDValues(lastValues.kp+dp, lastValues.ki+di, lastValues.kd+dd);
	}

}
