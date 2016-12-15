package drive;

import comms.DebugMode;
import comms.SmartWriter;
import comms.XboxController;
import physicalOutput.IMotor;

/**
 * An arcade drive that uses only the left JoyStick to move. <br>
 * <br>
 * Y-Axis: forward/backwards speed <br>
 * X-Axis: turn left/right
 * 
 * @author SecondThread
 */
public class BetaArcadeDrive extends IDrive {

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
	private XboxController controller;

	/**
	 * holds the values to pass to the motors from when they are calculated in
	 * TeleopPeriodic to when they are suppose to be passed to the motors
	 */
	private double leftMotors=0, rightMotors=0;

	// size of the joy stick dead zone
	private final double XDEADZONE=0.05;
	private final double YDEADZONE=0.05;

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
	public BetaArcadeDrive(IMotor fl, IMotor fr, IMotor bl, IMotor br) {
		this.frontLeft=fl;
		this.frontRight=fr;
		this.backLeft=bl;
		this.backRight=br;
		controller=XboxController.getXboxController();
	}

	/**
	 * Calculates the values that should be passed to the left motors and right
	 * motors and stores them in leftMotor and rightMotor<br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none<br>
	 */
	protected void teleopUpdate() {

		// Position of joy stick
		double joystickX=controller.getLeftJoystickX();
		double joystickY=controller.getLeftJoystickY();

		// variables to store the max sum and difference
		double max;
		double sum;
		double difference;

		// check to see if within X dead zone
		if (joystickX<XDEADZONE&&joystickX>-XDEADZONE) {
			joystickX=0;
		}

		// check to see within Y dead zone
		if (joystickY<YDEADZONE&&joystickY>-YDEADZONE) {
			joystickY=0;
		}

		// here I set the max
		max=Math.abs(joystickX);

		if (Math.abs(joystickY)>max) {
			max=Math.abs(joystickY);
		}

		// here I set the sum
		sum=joystickX+joystickY;

		// here I set the difference
		difference=joystickX-joystickY;

		// what we do if we have input
		if (joystickX>0||joystickY>0||joystickX<0||joystickY<0) {

			if (joystickY>=0) {
				// check if in first quadrant
				if (joystickX>=0) {

					leftMotors=max;
					rightMotors=-difference;
				}

				// joystick is in quadrant 2
				else {
					leftMotors=difference;
					rightMotors=max;
				}
			}

			else {
				// check if in quadrant 4
				if (joystickX>=0) {

					leftMotors=sum;
					rightMotors=-max;
				}

				// joystick is in quadrant 3
				else {
					leftMotors=-max;
					rightMotors=-sum;
				}
			}
		}

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
