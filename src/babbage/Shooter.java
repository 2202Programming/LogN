package babbage;

import comms.SmartWriter;
import physicalOutput.IMotor;
import physicalOutput.ServoMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class Shooter extends IControl {
	private IMotor shooterMotors;
	private BabbageControl controller;
	private double speed = -0.2;
	private ShooterState state;
	private Chamber shoosterChamber;
	private Turret shoosterTurret;
	// user input
	private boolean _startShoosting;
	private boolean _abort;
	private boolean _fire;
	private boolean _reverse;

	public Shooter(IMotor motors, IMotor newChamber, ServoMotor turret, ServoMotor bturret) {
		shooterMotors = motors;
		controller = (BabbageControl) Global.controlObjects.get("CONTROL");
		shoosterChamber = new Chamber(newChamber);
		shoosterTurret = new Turret(turret, bturret);
	}

	/**
	 * gets all user input for the shooter and sets the respective instance fields
	 */
	public void updateUserInput() {
		//if the user wants to start shooting
		_startShoosting = controller.startShooting();
		//Can be pressed at any time to stop shooting 
		_abort = controller.stopShooting();
		//If we are primed, shoot the ball
		_fire = state == ShooterState.PRIMED?controller.startShooting():false;
		//If we are primed, reverse the shooter motors to unclog balls
		_reverse = state == ShooterState.PRIMED||state == ShooterState.REVERSE?controller.reverseShooter():false;
	}

	/**
	 * displays user input for debug purpose
	 */
	public void displayUserInput() {
		SmartWriter.putS("ShooterState", state.toString());
		SmartWriter.putB("_startShoosting", _startShoosting);
		SmartWriter.putB("_abort", _abort);
		SmartWriter.putB("_fire", _fire);
		SmartWriter.putB("_reverse", _reverse);
	}

	/**
	 * sets motor speed to 0 and state to IDLE
	 */
	public void teleopInit() {
		shooterMotors.setSpeed(0);
		shoosterChamber.init();
		state = ShooterState.IDLE;
	}

	public void teleopPeriodic() {
		//get all user input
		updateUserInput();
		//display user input
		displayUserInput();
		//reset everything if abort
		if (_abort) {
			teleopInit();
		}
		//If the user wants to start firing, begin to wind the motors
		if (_startShoosting && state == ShooterState.IDLE) {
			state = ShooterState.WINDUP;
		}
		//Once the motors are wound up, we go the primed state
		if (state == ShooterState.WINDUP) {
			if (windUp()) {
				state = ShooterState.PRIMED;
			}
		}
		//Shoot the ball
		if (_fire) {
			//TODO check if balls are in the shooter and make some decision about that
			shoot();
		}else{
			shoosterChamber.init();
		}
		//Reverse the shooter motor if Primed
		if (_reverse) {
			reverse();
			state = ShooterState.REVERSE;
		}
		else {
			if (state == ShooterState.REVERSE) {
				//If we just reversed, we need to wind up the motors again
				state = ShooterState.WINDUP;
			}
		}
		// Turret code starts
		shoosterTurret.setAngle(controller.getLeftJoystickX(1));
		//shoosterTurret.setHeight(controller.getRightJoystickY(1));
	}

	/**
	 * Winds up the shooter motors
	 * @return true if the motors are at speed
	 */
	public boolean windUp() {
		shooterMotors.setSpeed(speed);
		// TODO checks motor speed
		return true;
	}
	
	/**
	 * Tell the chamber to shoot the ball
	 * @return true if there are balls to shoot
	 */
	public boolean shoot() {
		return shoosterChamber.shoot();
	}

	/**
	 * Sets the motors backwards
	 * @return true always(may change)
	 */
	public boolean reverse() {
		shooterMotors.setSpeed( -speed);
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
	
	public void init(){
		chamber.setSpeed(0);
	}

	/**
	 * Tell the chamber to start shooting<br>
	 * should only be called when shooter is at speed
	 * 
	 * @return true if there are balls in the shooter currently
	 */
	public boolean shoot() {
		chamber.setSpeed(-0.3);
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
		turretMotor.setSpeed(angle);
	}
	public void setHeight(double height) {
		angleMotor.setSpeed(height);
	}
}