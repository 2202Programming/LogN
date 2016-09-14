package PID;

public interface AutoPIDTunable {

	/**
	 * A command sent to this tunable object that tells it to start resetting it
	 * to a constant state a fixed amount from the desired angle
	 */
	void startReset();

	/**
	 * A command sent to this tunable object that set its to some random state.
	 * This is not used in actual PID Tuning
	 */
	void setToRandomState();

	/**
	 * Called to check if the resetting is finished or not. If not, this will be
	 * called every frame until the reset is finished.
	 * 
	 * @return True if and only if the reset is finished.
	 */
	boolean getResetFinished();

	/**
	 * Gets the current error. All tunable objects must have some way of
	 * measuring what needs to be tuned. <br>
	 * We always want 0 error.
	 * 
	 * @return The error, which can be any double in the range, -infinity to
	 *         infinity, but works best when between -tau/2 and tau/2, both
	 *         inclusive
	 */
	double getError();

	/**
	 * Sets the value that should be directly applied to the motors to make the
	 * wheels spin<br>
	 * THIS IS NOT GUARENTEED TO BE BETWEEN 0 AND 1!
	 * 
	 * @param turnValue
	 *            The thing being sent directly to the motors.
	 */
	void setValue(double turnValue);
}
