package PID;

public interface AutoPIDTunable {
	
	/**
	 * A command sent to this tunable object that tells it to start resetting it to a constant state a fixed amount from the desired angle
	 */
	void startReset();
	
	/**
	 * A command sent to this tunable object that set its to some random state. This is not used in actual PID Tuning
	 */
	void setToRandomState();
	
	/**
	 * Called to check if the resetting is finished or not. If not, this will be called every frame until the reset is finished.
	 * @return
	 * True if and only if the reset is finished.
	 */
	boolean getResetFinished();
		
	
	
	/**
	 * Gets the current error. All tunable objects must have some way of measuring what needs to be tuned.
	 * @return
	 * The error, which can be any double in the range, -infinity to infinity, but works best when between -tau/2 and tau/2, both inclusive
	 */
	double getError();
	
	/**
	 * Sets the target value the PID loop should aim for.
	 * @param turnValue
	 * The value the PID loop should aim for, usually measured in radians
	 */
	void setValue(double turnValue);
}
