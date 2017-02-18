package babbage;

import physicalOutput.IMotor;
import physicalOutput.SparkMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class Intake extends IControl {
	private IMotor[] intakeMotors;
	private BabbageControl controller;
	private double speed = 0;

	// 775 motors
	// 2 motors ports 4 and 5
	public Intake(IMotor[] motors) {
		intakeMotors = motors;
	}

	public void robotInit() {
	}

	public void autonomousInit() {
		controller = (BabbageControl)Global.controllers;
	}

	public void autonomousPeriodic() {
		update();
	}

	public void teleopInit() {
		controller = (BabbageControl)Global.controllers;
	}

	public void teleopPeriodic() {
		update();
	}

	public void disabledInit() {
		speed = 0;
	}

	private void update() {
		System.out.println(controller.intakeEngaged());
		if (controller.intakeEngaged()) {
			if (controller.intakeSpeed()) {
				speed = 1;
			}
			else {
				speed = 0.5;
			}
		}else{
			speed = 0;
		}
		for (IMotor motor : intakeMotors) {
			motor.setSpeed(speed);
		}
	}
}