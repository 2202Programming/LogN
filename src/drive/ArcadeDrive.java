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
public class ArcadeDrive extends IDrive {

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
		double stickXSquare=Math.abs(controller.getLeftJoystickX());
		double stickYSquare=Math.abs(controller.getLeftJoystickY());
		double radiusSquare=getRadiusOfSquare(stickXSquare, stickYSquare);

		// Normalize all stick inputs to have a maximum length of 1, because rawInput
		// can be anywhere in the square with diagonals (-1, -1) and (1, 1)
		double stickX=stickXSquare/radiusSquare;
		double stickY=stickYSquare/radiusSquare;
		
		// convert from Cartesian to polar so things work later
		double radius=Math.sqrt(stickX*stickX+stickY*stickY);

		// example coordinates --> leftPower : right power
		// (0, 1) --> 1 : 1
		// (1, 0) --> 1 : -1
		// (-1, 0) --> -1 : 1
		// (0, -1) --> -1 : -1

		// the amount of turn is 2*stickX because the difference between the
		// left and right at full turn is 2, and the max x is 1
		double differenceBetweenMotors=2*stickX;
		double maxMotor=1;
		double minMotor=maxMotor-differenceBetweenMotors;

		// scale the motor values back depending on how far the joystick is
		// pressed
		maxMotor*=radius;
		minMotor*=radius;

		if (stickX>0) {// turning right
			leftMotors=maxMotor;
			rightMotors=minMotor;
		}
		else {// turning left
			leftMotors=minMotor;
			rightMotors=maxMotor;
		}

		// If we are going backwards, the left and right motors need to be made
		// negative. If we are also turning, then they have to be switched with
		// each other as well.
		if (stickY<0) {
			leftMotors*=-1;
			rightMotors*=-1;
			double temp=leftMotors;
			leftMotors=rightMotors;
			rightMotors=temp;
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

	/**
	 * Gets the distance from the origin to the edge of the unit square along
	 * the line that passes through both the origin and (<i>stickXSquare</i>,
	 * <i>stickYSquare</i>)
	 * 
	 * @param stickXSquare
	 *            The x position of the joystick
	 * @param stickYSquare
	 *            The y position of the joystick
	 * @return A distance value between 1 and sqrt(2), both inclusive.
	 */
	private double getRadiusOfSquare(double stickXSquare, double stickYSquare) {
		double theta=Math.abs(Math.atan2(stickYSquare, stickXSquare));
		if (theta>Math.PI/2) {
			double squareXIntercept=stickXSquare/stickYSquare;
			double squareYIntercept=1;
			double rSquare=Math.hypot(squareXIntercept, squareYIntercept);
			return rSquare;

		}
		else {
			double squareXIntercept=1;
			double squareYIntercept=stickXSquare/stickYSquare;
			double rSquare=Math.hypot(squareXIntercept, squareYIntercept);
			return rSquare;
		}
	}
}
