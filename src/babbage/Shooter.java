package babbage;

import comms.SmartWriter;
import comms.XboxController;
import physicalOutput.IMotor;
import robot.IControl;

public class Shooter extends IControl {
	private IMotor shooterMotors;
	private XboxController controller;
	private double speed = 0;
	private ShooterState state;
	private Chamber shoosterChamber;
	private Turret shoosterTurret;
	// user input
	private boolean _startShoosting;
	private boolean _abort;
	private boolean _fire;
	private boolean _reverse;

	// 1 motor port ??
	public Shooter(IMotor motors, IMotor newChamber, IMotor turret) {
		shooterMotors = motors;
		controller = XboxController.getXboxController();
		shoosterChamber = new Chamber(newChamber);
		shoosterTurret = new Turret(turret);
	}

	public void updateUserInput() {
		_startShoosting = controller.getRightTriggerPressed();
		_abort = state == ShooterState.PRIMED || state == ShooterState.WINDUP || state == ShooterState.REVERSE
				?controller.getBPressed():false;
		_fire = state == ShooterState.PRIMED?controller.getRightTriggerPressed():false;
		_reverse = state == ShooterState.PRIMED?controller.getRightBumperPressed():false;
	}

	public void displayUserInput() {
		SmartWriter.putB("_startShoosting", _startShoosting);
		SmartWriter.putB("_abort", _abort);
		SmartWriter.putB("_fire", _fire);
		SmartWriter.putB("_reverse", _reverse);
	}

	public void teleopInit() {
		shooterMotors.setSpeed(0);
		state = ShooterState.IDLE;
	}

	public void teleopPeriodic() {
		updateUserInput();
		if (_abort) {
			teleopInit();
		}
		if (_startShoosting && state == ShooterState.IDLE) {
			state = ShooterState.WINDUP;
		}

		if (state == ShooterState.WINDUP) {
			if (windUp()) {
				state = ShooterState.PRIMED;
			}
		}

		if (_startShoosting && state == ShooterState.PRIMED) {
			primed();
		}

		if (state == ShooterState.PRIMED && _reverse) {
			reverse();
		}
		else {
			if (state == ShooterState.REVERSE) {
				state = ShooterState.WINDUP;
			}
		}
		// Turret code starts

	}

	public boolean windUp() {
		shooterMotors.setSpeed(0.5);
		// TODO checks motor speed
		return true;
	}

	public boolean primed() {
		return shoosterChamber.shoot();
	}

	public boolean reverse() {
		shooterMotors.setSpeed(-0.5);
		return true;
	}

}

class Chamber {
	private IMotor chamber;

	public Chamber(IMotor newChamber) {
		chamber = newChamber;

	}

	public boolean shoot() {
		chamber.setSpeed(0.5);
		return true;
	}
}

class Turret {
	private IMotor turretMotor;

	public Turret(IMotor tMotor) {
		turretMotor = tMotor;
	}

	public void setAngle(double angle) {
		// TODO set angle here
	}
}