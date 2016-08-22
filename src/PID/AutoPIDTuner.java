package PID;

public class AutoPIDTuner {

	private AutoPIDTunable toTune;
	private PIDValues bestPreviousPIDValues=new PIDValues(0.1, 0, 0);
	private PIDController pidController=new PIDController();
	
	public AutoPIDTuner(AutoPIDTunable toTune) {
		this.toTune=toTune;
	}

	public void update() {
		tunePID();
	}

	
	/**
	 * <h1>Evolutionary PID Tuning Pro Strat:</h1>
	 * <br>
	 * <br>
	 * Start with <.1, 0, 0> <br>
	 * Test and record time
	 * <br>
	 * <br>
	 * Change one number a little bit.<br>
	 * Test and record the new time
	 * <br>
	 * <br>
	 * If the new one is better, start from there and keep doing the same thing,
	 * otherwise, try another random mutation
	 */
	private void tunePID() {
		if (!toTune.getResetFinished()) {
			return;
		}
		double output=pidController.calculate(0, toTune.getError());
		toTune.setValue(output);
	}
	
}
