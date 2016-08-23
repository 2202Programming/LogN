package PID;

import java.util.ArrayList;
import java.util.Random;

import comms.FileLoader;

public class AutoPIDTuner {

	private AutoPIDTunable toTune;
	private PIDValues bestPIDValues=new PIDValues(.4, .03, 0), testingPIDValues=new PIDValues(.4, .03, 0);
	private PIDController pidController=new PIDController(testingPIDValues);
	private double minError=0.01;
	private int errorSafeCounter=0, maxErrorSafeCounter=40, currentTuneCounter=0, maxTuneCounter=1200;
	private int bestTuneTime=maxTuneCounter;

	private double dp=0, di=0, dd=0;
	private int timesTried=0;

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
		if (timesTried>100) {
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

			toWrite.add(timesTried+","+testingPIDValues.kp+","+testingPIDValues.ki+","+testingPIDValues.kp+","+currentTuneCounter
					+",14,"+(currentTuneCounter<bestTuneTime?1:0));
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
			currentTuneCounter=0;
			toTune.startReset();
			timesTried++;
			if (timesTried%10==0) {
				System.out.println("\n\nGeneration"+timesTried+"\n\n");
			}
			return;
		}
		double output=pidController.calculate(0, toTune.getError());
		toTune.setValue(output);
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
		double divider=2;//Math.log(timesTried+2);
		dp=Math.pow(r.nextDouble()-0.5, 3)*4/divider;
		di=Math.pow(r.nextDouble()-0.5, 3)/15/divider;
		dd=Math.pow(r.nextDouble()-0.5, 3)/10/divider;

		return new PIDValues(lastValues.kp+dp, lastValues.ki+di, lastValues.kd+dd);
	}

	private PIDValues extrapolate(PIDValues lastValues) {
		return new PIDValues(lastValues.kp+dp, lastValues.ki+di, lastValues.kd+dd);
	}

}
