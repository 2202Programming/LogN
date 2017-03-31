package babbage;

import comms.DebugMode;
import comms.SmartWriter;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.ServoMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.BabbageControl;

public class Shooter extends IControl {
	private IMotor shooterMotors;
	private IMotor agitatorMotor;
	private BabbageControl controller;
	private double speed = 1100;
	private ShooterState state;
	private Chamber shoosterChamber;
	private Turret shoosterTurret;
	// user input
	private boolean _startShoosting;
	private boolean _abort;
	private boolean _fire;
	private boolean _windDown;
	
	private boolean autoMode;
	private boolean shouldShoot;

	private boolean direction;
	private int agCounter;

	public Shooter(IMotor motors, IMotor newChamber, IMotor agitator, ServoMotor turret, ServoMotor bturret) {
		shooterMotors = motors;
		controller = (BabbageControl)Global.controllers;
		shoosterChamber = new Chamber(newChamber);
		shoosterTurret = new Turret(turret, bturret);
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
		shoosterChamber.init();
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
			agitate();
		}
		// Shoot the ball
		if (_fire) {
			// TODO check if balls are in the shooter and make some decision
			// about that
			shoot();
		}
		else {
			shoosterChamber.init();
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
		// Turret code starts
		//If the start button is pressed, the turret starts using joystick controls.
		//the servo takes a value 0-1
		if (controller.pauseHighGoalVision()) {
			shoosterTurret.setAngle((controller.getLeftJoystickX(1) + 1) / 2f);
			shoosterTurret.setHeight((controller.getRightJoystickY(1) + 1) / 2f);
			//shoosterTurret.setHeight(0.05);
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

	private void agitate() {
		if (direction) {
			agitatorMotor.set(0.6);
		}
		else {
			agitatorMotor.set( -0.6);
		}

		if ( ++agCounter > 50) {
			direction = !direction;
			agCounter = 0;
		}
	}

	/**
	 * Tell the chamber to shoot the ball
	 * 
	 * @return true if there are balls to shoot
	 */
	public boolean shoot() {
		return shoosterChamber.shoot();
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

/**
 * This is the class for the chamber that feeds balls into the shooter
 * 
 * @author Daniel
 *
 */
class Chamber {
	private IMotor chamber;

	/**
	 * Create a new chamber
	 * 
	 * @param newChamber
	 *            the motor assigned to the chamber
	 */
	public Chamber(IMotor newChamber) {
		chamber = newChamber;

	}

	public void init() {
		chamber.set(0);
	}

	/**
	 * Tell the chamber to start shooting<br>
	 * should only be called when shooter is at speed
	 * 
	 * @return true if there are balls in the shooter currently
	 */
	public boolean shoot() {
		chamber.set( -0.7);
		// TODO check if there are balls in the shooter
		return true;
	}
}

/**
 * This class controls the angle rotation of the shooter's turn table
 * 
 * @author Daniel
 *
 */
class Turret {
	private IMotor turretMotor;
	private IMotor angleMotor;

	/**
	 * Create a new turret
	 * 
	 * @param tMotor
	 *            the motor that controls the turret; must be a servo
	 */
	public Turret(IMotor tMotor, IMotor aMotor) {
		turretMotor = tMotor;
		angleMotor = aMotor;
	}

	/**
	 * sets the angle of the turret
	 * 
	 * @param angle
	 *            the angle of the turret Preconditions: the angle must be
	 *            between -1 and 1 Postconditions: the angle is set to the ratio
	 *            between the max and min angles
	 */
	public void setAngle(double angle) {
		// for a servo this actually sets the angle not the speed
		turretMotor.set(angle);
	}

	public void setHeight(double height) {
		angleMotor.set(height);
	}
}