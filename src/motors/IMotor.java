package motors;

import robot.IControl;

//leinad's stuff

/**
 * 
 */
public abstract class IMotor extends IControl {

	/**
	 * Default constructor
	 */
	public IMotor() {
	}

	/**
	 * 
	 */
	protected boolean enabled;

	/**
	 * 
	 */
	protected double setValue;

	/**
	 * @param x
	 */
	protected abstract void setMotor(double x);

	/**
	 * 
	 */
	public void teleopInit() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void teleopPeriodic() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void autoInit() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void autoPeridic() {
		// TODO implement here
	}

	/**
	 * Sets whether this motor is enabled or not to <i>enabled</i> <br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: If <i>enabled</i> is false, this motor will become or
	 * stay disabled. If <i>enabled</i>is true, this motor will become or stay
	 * enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled=enabled;
	}
}