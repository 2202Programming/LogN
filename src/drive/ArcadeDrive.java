package drive;

import comms.DebugMode;
import comms.SmartWriter;
import physicalOutput.motors.IMotor;
import robot.Global;
import robotDefinitions.ControlBase;

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
	private ControlBase controller;

	private boolean drivingIsFlipped=false;
	
	/**
	 * holds the values to pass to the motors from when they are calculated in
	 * TeleopPeriodic to when they are suppose to be passed to the motors
	 */
	private double leftMotors=0, rightMotors=0;

	/**
	 * This makes it easier to drive robots that tip
	 */
	private double maxAcceleration=100000;
	
	public ArcadeDrive(IMotor fl, IMotor fr) {
		this.frontLeft=fl;
		this.frontRight=fr;
		controller=Global.controllers;
	}
	
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
		controller=Global.controllers;
	}
	
	public ArcadeDrive(IMotor fl, IMotor fr, IMotor bl, IMotor br, double maxAcceleration) {
		this.frontLeft=fl;
		this.frontRight=fr;
		this.backLeft=bl;
		this.backRight=br;
		this.maxAcceleration=maxAcceleration;
		controller=Global.controllers;
	}
	
	public void teleopInit() {
		autonomousInit();
	}
	
	public void autonomousInit() {
		drivingIsFlipped=false;
	}

	/**
	 * Sets <i>leftMotors</i> and <i>rightMotors</i> to what they are suppose to
	 * be using the leftJoystick of the XboxController as input <br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none<br>
	 */
	protected void teleopUpdate() {
		double stickXSquare=controller.getLeftJoystickX();
		double stickYSquare=controller.getLeftJoystickY();

		Vector2 output=getMotorOutputs(stickXSquare, stickYSquare);

		double leftTarget=output.getX();
		double rightTarget=output.getY();
		if (Math.abs(leftTarget-leftMotors)<maxAcceleration) {
			leftMotors=leftTarget;
		}
		else {
			leftMotors+=Math.signum(leftTarget-leftMotors)*maxAcceleration;
		}
		
		if (Math.abs(rightTarget-rightMotors)<maxAcceleration) {
			rightMotors=rightTarget;
		}
		else {
			rightMotors+=Math.signum(rightTarget-rightMotors)*maxAcceleration;
		}
		
		SmartWriter.putD("LeftMotors", leftMotors, DebugMode.DEBUG);
		SmartWriter.putD("RightMotors", rightMotors, DebugMode.DEBUG);
	}

	/**
	 * Sets the left side motor power<br>
	 * Preconditions: speed is between -1.0 and 1.0<br>
	 * Postconditions: sets the motors
	 */
	private void setLeftMotorsRaw(double speed) {
		if (drivingIsFlipped) {
			speed*=-1;
			if (frontRight!=null) frontRight.set(speed);
			if (backRight!=null) backRight.set(speed);
		}
		else {			
			if (frontLeft!=null) frontLeft.set(speed);
			if (backLeft!=null) backLeft.set(speed);
		}
	}

	/**
	 * Sets the right side motor power<br>
	 * Preconditions: speed is between -1.0 and 1.0<br>
	 * Postconditions: sets the motors
	 */
	private void setRightMotorsRaw(double speed) {
		if (drivingIsFlipped) {
			speed*=-1;
			if (frontLeft!=null) frontLeft.set(speed);
			if (backLeft!=null) backLeft.set(speed);
		}
		else {
			if (frontRight!=null) frontRight.set(speed);
			if (backRight!=null) backRight.set(speed);
		}
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
		if(super.driveControl == DriveControl.EXTERNAL_CONTROL)
			setLeftMotorsRaw(power);
	}

	// comments in IDrive
	public void setRightMotors(double power) {
		if(super.driveControl == DriveControl.EXTERNAL_CONTROL)
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
	private static double getRadiusOfSquare(double stickXSquare, double stickYSquare) {
		//This is legal because all four quadrants of the unit square are symmetric about the origin 
		stickXSquare=Math.abs(stickXSquare);
		stickYSquare=Math.abs(stickYSquare);
		
		double theta=Math.atan2(stickYSquare, stickXSquare);
		if (theta<Math.PI/4) {
			double squareXIntercept=stickYSquare/stickXSquare;
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

	/**
	 * Calculates the values that should be passed to the left motors and right
	 * motors and returns them as a vector in the form (<i>leftMotorOutput</i>,
	 * <i>rightMotorOutput</i>)<br>
	 * <br>
	 * 
	 * @param leftJoystickXInput
	 *            The x input for the joystick on the unit square input range
	 *            (from -1 to 1)
	 * @param leftJoystickYInput
	 *            The y input for the joystick on the unit square input range
	 *            (from -1 to 1)
	 * 
	 * @return a Vector2 with the x coordinate being the leftMotorPower, and the
	 *         y coordinate being the rightMotorPower
	 */
	private static Vector2 getMotorOutputs(double leftJoystickXInput, double leftJoystickYInput) {
		double stickXSquare=leftJoystickXInput*Math.abs(leftJoystickXInput)*Math.abs(leftJoystickXInput)*Math.abs(leftJoystickXInput);
		double stickYSquare=leftJoystickYInput;
		double radiusSquare=Math.abs(getRadiusOfSquare(stickXSquare, stickYSquare));
		

		// Normalize all stick inputs to have a maximum length of 1, because
		// rawInput can be anywhere in the square with diagonals (-1, -1) and
		// (1, 1)
		double stickX=stickXSquare/radiusSquare;
		double stickY=stickYSquare/radiusSquare;
		
		

		// convert from Cartesian to polar so things work later
		double radius=Math.hypot(stickX, stickY);

		// example coordinates --> leftPower : right power
		// (0, 1) --> 1 : 1
		// (1, 0) --> 1 : -1
		// (-1, 0) --> -1 : 1
		// (0, -1) --> -1 : -1

		// the amount of turn is 2*stickX because the difference between the
		// left and right at full turn is 2, and the max x is 1
		double differenceBetweenMotors=2*Math.abs(stickX/radius);
		double maxMotor=1;
		double minMotor=maxMotor-differenceBetweenMotors;

		// scale the motor values back depending on how far the joystick is
		// pressed
		maxMotor*=radius;
		minMotor*=radius;

		double leftMotorsTemp=0, rightMotorsTemp=0;

		if (stickX>0) {// turning right
			leftMotorsTemp=maxMotor;
			rightMotorsTemp=minMotor;
		}
		else {// turning left
			leftMotorsTemp=minMotor;
			rightMotorsTemp=maxMotor;
		}

		// If we are going backwards, the left and right motors need to be made
		// negative. If we are also turning, then they have to be switched with
		// each other as well.
		if (stickY<0) {
			leftMotorsTemp*=-1;
			rightMotorsTemp*=-1;
			double temp=leftMotorsTemp;
			leftMotorsTemp=rightMotorsTemp;
			rightMotorsTemp=temp;
		}

		return new Vector2(leftMotorsTemp, rightMotorsTemp);
	}
	
	
	public void setReverse(boolean reversed) {
		drivingIsFlipped=reversed;
	}
	
}

class Vector2 {
	
	private double x, y;

	public Vector2() {
		x=y=0;
	}

	public Vector2(double x, double y) {
		this.x=x;
		this.y=y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String toString() {
		return "< x: "+String.format("%.2f", x)+", y: "+String.format("%.2f", y)+">";
	}
	
	
}
