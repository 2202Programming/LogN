package motors;

import robot.IControl;

//leinad's stuff

/**
 * 
 */
public abstract class IMotor extends IControl {

	/**
	 * Default constructor disables the motor and sets setValue to 0.0;
	 */
	public IMotor() {
		enabled = false;
		setValue = 0.0;
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
	 * Set speed to zero in init
	 */
	public void teleopInit() {
		this.setMotor(0.0);
	}

	/**
	 * If enabled: Set the speed of the motor every cycle
	 */
	public void teleopPeriodic() {
		if(this.enabled)
			this.setMotor(setValue);
		else
			this.setMotor(0.0);
	}

	/**
	 * Set speed to zero in init
	 */
	public void autoInit() {
		this.setMotor(0.0);
	}

	/**
	 * If enabled: Set the speed of the motor every cycle
	 */
	public void autoPeridic() {
		if(this.enabled)
			this.setMotor(setValue);
		else
			this.setMotor(0.0);
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
	
	/**
	 * Sets the value that will be applied to the motor
	 * Preconditions: The value inputed is between -1.0 and 1.0
	 * Postconditions: setValue will be updated
	 * @param xSpeed
	 */
	public void setSpeed(double xSpeed){
		setValue = xSpeed;
	}
}