package physicalOutput;

import robot.IControl;

//leinad's stuff

/**
 * The super class for motors
 */
public abstract class IMotor extends IControl {

	/**
	 * Not necessarily used by anything; make sure we use this, because I don't think anyone does
	 */
	protected boolean enabled;

	/**
	 * default value to set motors but is overriden by subclasses I think
	 */
	protected double setValue;

	/**
	 * True if the values of the motors is really -1 times what they should be sent
	 */
	protected boolean reverse;

	/**
	 * Default constructor disables the motor and sets setValue to 0.0;
	 */
	public IMotor() {
		this(false);
	}

	public IMotor(boolean reverse) {
		enabled=false;
		setValue=0.0;
		this.reverse=reverse;
	}

	/**
	 * @param x
	 */
	protected abstract void setMotor(double x);

	/**
	 * Set speed to zero in init
	 */
	public void teleopInit() {
		enabled=true;
		this.setMotor(0.0);
	}

	/**
	 * If enabled: Set the speed of the motor every cycle otherwise: Set speed
	 * to 0
	 */
	public void teleopPeriodic() {
		if (this.enabled) this.setMotor(setValue);
		else
			this.setMotor(0.0);
	}

	/**
	 * Set speed to zero in init
	 */
	public void autonomousInit() {
		enabled = true;
		this.setMotor(0.0);
	}

	/**
	 * If enabled: Set the speed of the motor every cycle
	 */
	public void autonomousPeriodic() {
		if (this.enabled) this.setMotor(setValue);
		else
			this.setMotor(0.0);
	}
	
	public void diabledInit()
	{
		enabled = false;
		setMotor(0);
	}

	/**
	 * Sets whether this motor is enabled or not to <i>enabled</i> <br>
	 * <br>
	 */
	public void setEnabled(boolean enabled) {
		this.enabled=enabled;
	}

	/**
	 * Sets the value that will be applied to the motor and reverses if
	 * necessary<br>
	 * Preconditions: The value inputed is between -1.0 and 1.0<br>
	 * Postconditions: setValue will be updated
	 * 
	 * @param xSpeed
	 */
	public void setSpeed(double xSpeed) {
		if (reverse) {
			xSpeed*=-1;
		}
		setValue=xSpeed;
	}
}