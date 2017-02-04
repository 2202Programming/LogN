package babbage;

import comms.SmartWriter;
import comms.XboxController;
import physicalOutput.IMotor;
import robot.IControl;

public class Shooter extends IControl {
	private IMotor[] shooterMotors;
	private XboxController controller;
	private double speed = 0;
	private ShooterState state;

	// user input
	private boolean _startShoosting;
	private boolean _abort;
	private boolean _fire;
	private boolean _reverse;

	// 1 motor port ??
	public Shooter(IMotor[] motors) {
		shooterMotors = motors;
		controller = XboxController.getXboxController();
	}

	public void updateUserInput() {
		_startShoosting = controller.getRightTriggerPressed();
		_abort = state == ShooterState.PRIMED || state == ShooterState.WINDUP?controller.getBPressed():false;
		_fire = state == ShooterState.PRIMED?controller.getRightTriggerPressed():false;
		_reverse = state == ShooterState.PRIMED?controller.getRightBumperPressed():false;
	}
	
	public void displayUserInput(){
		SmartWriter.putB("_startShoosting", _startShoosting);
	}

	public void teleopInit() {
		for (IMotor i : shooterMotors)
			i.setSpeed(0);
	}

	public void teleopPeriodic() {
		updateUserInput();
	}

}

class Chamber {

}