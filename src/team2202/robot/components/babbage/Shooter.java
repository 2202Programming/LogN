package team2202.robot.components.babbage;

import comms.DebugMode;
import comms.SmartWriter;
import physicalOutput.motors.IMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.BabbageControl;

public class Shooter extends IControl {
	private IMotor shooterMotors;
	private IMotor agitatorMotor;
	private BabbageControl controller;
	private double speed = 1100;
	private ShooterState state;
	// user input
	private boolean _startShoosting;
	private boolean _abort;
	private boolean _fire;
	private boolean _windDown;
	
	private boolean autoMode;
	private boolean shouldShoot;

	public Shooter(IMotor motors, IMotor newChamber, IMotor agitator) {
		shooterMotors = motors;
		controller = (BabbageControl)Global.controllers;
		agitatorMotor = agitator;
	}

	/**
	 * gets all user input for the shooter and sets the respective instance
	 * fields
	 */
	public void updateUserInput() {
		// if the user wants to start shooting
		_startShoosting = controller.startShooting();
		// If we are primed, shoot the ball
		_fire = state == ShooterState.PRIMED?controller.startShooting():false;
		// If we are primed, reverse the shooter motors to unclog balls
		_windDown = (state == ShooterState.PRIMED || state == ShooterState.REVERSE)?controller.reverseShooter():false;
	}

	/**
	 * displays user input for debug purpose
	 */
	public void displayUserInput() {
		SmartWriter.putS("ShooterState", state.toString());
		SmartWriter.putB("_startShoosting", _startShoosting);
		SmartWriter.putB("_fire", _fire);
		SmartWriter.putB("_reverse", _windDown);
	}
	/**
	 * Sets whether the shooter should shoot autonomously<br>
	 * This only is active during autonomous periodic at this time
	 * @param shouldShoot if the shooter should shoot or not
	 */
	public void setAutoShoot(boolean shouldShoot){
		this.shouldShoot = shouldShoot;
	}

	public void autonomousInit() {
		autoMode = true;
		shouldShoot = false;
		init();
	}

	public void teleopInit() {
		autoMode = false;
		init();
	}

	/**
	 * sets motor speed to 0 and state to IDLE
	 */
	public void init() {
		shooterMotors.set(0);
		agitatorMotor.set(0);
		state = ShooterState.IDLE;

		direction = true;
		agCounter = 0;
	}

	public void teleopPeriodic() {
		update();
	}
	
	public void autonomousPeriodic(){
		update();
	}

	public void update() {
		// get all user input
		updateUserInput();
		// display user input
		displayUserInput();
		if(autoMode){
			_fire = _startShoosting = shouldShoot;
		}

		SmartWriter.putD("ShooterSetSpeed", speed, DebugMode.COMPETITION);
		// if(controller.shooterSpeedToggle()){
		// speed+=50;
		// if (speed>=1250) {
		// speed=1000;
		// state=ShooterState.WINDUP;
		// }
		// }
		// If the user wants to start firing, begin to wind the motors
		if (_startShoosting && state == ShooterState.IDLE) {
			state = ShooterState.WINDUP;
		}
		// Once the motors are wound up, we go the primed state
		if (state == ShooterState.WINDUP) {
			if (windUp()) {
				state = ShooterState.PRIMED;
			}
		}

		if (state == ShooterState.PRIMED) {
		}
		// Shoot the ball
		if (_fire) {
			// TODO check if balls are in the shooter and make some decision
			// about that
			shoot();
		}
		else {
			stopShoot();
		}
		// Reverse the shooter motor if Primed
		if (_windDown) {
			windDown();
			state = ShooterState.REVERSE;
		}
		else {
			if (state == ShooterState.REVERSE) {
				teleopInit();
			}
		}
	}

	/**
	 * Winds up the shooter motors
	 * 
	 * @return true if the motors are at speed
	 */
	private boolean windUp() {
		shooterMotors.set(speed);
		// TODO checks motor speed
		return true;
	}

	/**
	 * Tell the chamber to shoot the ball
	 * 
	 * @return true if there are balls to shoot
	 */
	public boolean shoot() {
		agitatorMotor.set(1);
		return true;
	}
	
	public void stopShoot(){
		agitatorMotor.set(0);
	}

	/**
	 * Sets the motors backwards
	 * 
	 * @return true always(may change)
	 */
	public boolean windDown() {
		shooterMotors.set(0);
		return true;
	}

}