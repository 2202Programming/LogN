package drive;

import comms.DebugMode;
import comms.SmartWriter;
import physicalOutput.IMotor;
import robot.Global;
import robotDefinitions.ControlBase;

public class TankDrive extends IDrive  {

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
	 * Used for controller input
	 */
	private ControlBase controller;

	/**
	 * holds the values to pass to the motors from when they are calculated in
	 * TeleopPeriodic to when they are suppose to be passed to the motors
	 */
	private double leftMotors=0, rightMotors=0;

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
	
	public TankDrive(IMotor fl, IMotor fr, IMotor bl, IMotor br) {
		this.frontLeft=fl;
		this.frontRight=fr;
		this.backLeft=bl;
		this.backRight=br;
		controller=Global.controllers;
	}
	
	
	/**
	 * Sets <i>leftMotors</i> and <i>rightMotors</i> to what they are suppose to
	 * be using the leftJoystick of the XboxController for the leftMotor and the rightJoystick for the rightMotor as input <br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none<br>
	 */
	protected void teleopUpdate() {
		double leftStick=controller.getLeftJoystickY();
		double rightStick=controller.getRightJoystickY();
		leftMotors=leftStick;
		rightMotors=rightStick;

		SmartWriter.putD("LeftMotors", leftMotors, DebugMode.FULL);
		SmartWriter.putD("RightMotors", rightMotors, DebugMode.FULL);
	}

	/**
	 * Sets the left side motor power<br>
	 * Preconditions: speed is between -1.0 and 1.0<br>
	 * Postconditions: sets the motors
	 */
	private void setLeftMotorsRaw(double speed) {
		frontLeft.setSpeed(speed);
		backLeft.setSpeed(speed);
	}

	/**
	 * Sets the right side motor power<br>
	 * Preconditions: speed is between -1.0 and 1.0<br>
	 * Postconditions: sets the motors
	 */
	private void setRightMotorsRaw(double speed) {
		frontRight.setSpeed(speed);
		backRight.setSpeed(speed);
	}

	/**
	 * Checks to see if any of the motors have encoders, returns true if any of
	 * them do
	 */
	public boolean hasEncoders() {
		// TODO implement in SensorController branch
		return false;
	}

	// comments in IDrive, implementation is obvious
	protected void setMotors() {
		setLeftMotorsRaw(leftMotors);
		setRightMotorsRaw(rightMotors);
	}

	// comments in IDrive
	protected void disableMotors() {
		setLeftMotorsRaw(0);
		setRightMotorsRaw(0);
	}

	// comments in IDrive
	public void setLeftMotors(double power) {
		setLeftMotorsRaw(power);

	}
	
	// comments in IDrive
	public void setRightMotors(double power) {
		setRightMotorsRaw(power);
	}
}
	
