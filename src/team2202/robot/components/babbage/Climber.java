package team2202.robot.components.babbage;

import physicalOutput.motors.IMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.BabbageControl;

public class Climber extends IControl {
	private IMotor climber;
	private boolean turbo;
	private BabbageControl controllers;
	private int counter;

	public Climber(IMotor motor) {
		climber = motor;
		turbo = false;
		controllers = (BabbageControl)Global.controllers;
	}

	public void teleopInit() {
		climber.set(0);
		turbo = false;
		counter = 0;
	}

	public void teleopPeriodic() {
		turbo = controllers.climberHold();
		if (turbo) {
			climber.set(1);
		}
		else {
			if (controllers.climberOn()) {
				climber.set(.5);
			}
			else {
				climber.set( -0);
			}
		}
	}
}
