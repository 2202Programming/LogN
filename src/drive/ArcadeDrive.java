package drive;

import motors.IMotor;

/**
 *
 */
public class ArcadeDrive extends IDrive {

	/**
	 * True if the wheel motors are being controlled by ArcadeDrive, false
	 * otherwise.
	 */
	private boolean enabled;

	/**
	 * The motor corresponding to the front right wheel
	 */
	private IMotor frontRight;

	/**
	 * The motor corresponding to the front left wheel
	 */
	private IMotor frontLeft;

	/**
	 * The motor corresponding to the back right wheel
	 */
	private IMotor backRight;

	/**
	 * The motor corresponding to the back left wheel
	 */
	private IMotor backLeft;

	/**
	 * @param fl
	 *            The front left motor
	 * @param fr
	 *            The front right motor
	 * @param bl
	 *            The back left motor
	 * @param br
	 *            The back right motor
	 */
	public ArcadeDrive(IMotor fl, IMotor fr, IMotor bl, IMotor br) {
		enabled =true;
		this.frontLeft=fl; //TODO this.* statements are redundent.
		this.frontRight=fr;
		this.backLeft=bl;
		this.backRight=br;
	}

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
	 * Sets the left side motor power<br>
	 * Preconditions: speed is between -1.0 and 1.0<br>
	 * Postconditions: sets the motors
	 */
	public void setLeftMotors(double speed) {
		if (enabled) {
			frontLeft.setSpeed(speed);
			backLeft.setSpeed(speed);
		} // TODO If they are not enabled you should set them to zero.
	}

	/**
	 * Sets the right side motor power<br>
	 * Preconditions: speed is between -1.0 and 1.0<br>
	 * Postconditions: sets the motors
	 */
	public void setRightMotors(double speed) {
		if (enabled) {
			frontRight.setSpeed(speed);
			backRight.setSpeed(speed);
		}
	}

	/**
	 * Sets whether or not arcade drive should control the motors (disable if
	 * something else is taking control) <br>
	 * <br>
	 * Preconditions: none <br>
	 * Postconditions: If ArcadeDrive has been set to enabled, it will continue
	 * (or begin) to write to the motors. Otherwise, the motors will not be set.
	 *
	 * @param enabled
	 *            Whether or not this <i>ArcadeDrive</i> object should be
	 *            enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled=enabled;
	}

	/**
	 * Checks to see if this <i>ArcadeDrive</i> is enabled or not
	 *
	 * @return True if enabled, false otherwise
	 */
	public boolean getEnabled() {
		return enabled;
	}

	/**
	 * Checks to see if any of the motors have encoders
	 */
	public boolean hasEncoders(){
		boolean toReturn = false;
		if(frontLeft.hasEncoder() || frontRight.hasEncoder() || backLeft.hasEncoder() || backRight.hasEncoder()){
			toReturn = true;
		}
		return toReturn;
	}

}
