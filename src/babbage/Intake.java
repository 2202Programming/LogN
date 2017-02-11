package babbage;

import comms.XboxController;
import physicalOutput.IMotor;
import physicalOutput.SparkMotor;
import robot.IControl;

public class Intake extends IControl {
	private IMotor[] intakeMotors;
	private XboxController controller;
	private double speed=0;

	// 775 motors
	// 2 motors ports 4 and 5
	public Intake(IMotor[] motors) {
		intakeMotors=motors;
		intakeMotors[0]=new SparkMotor(4, true);
		intakeMotors[1]=new SparkMotor(5, true);
	}

	public void robotInit() {
	}

	public void autonomousInit() {
		controller=XboxController.getXboxController();
	}

	public void autonomousPeriodic() {
		update();
	}

	public void teleopInit() {
		controller=XboxController.getXboxController();
	}

	public void teleopPeriodic() {
		update();
	}

	public void disabledInit() {
		speed=0;
	}

	private void update() {
		if (controller.getAHeld()) {
			speed=0.8;
		}
		else {
			speed=0.5;
		}
		for (IMotor motor : intakeMotors) {
			motor.setSpeed(speed);
		}
	}
}