package babbage;

import physicalOutput.IMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

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
		climber.setSpeed(0);
		turbo = false;
		counter = 0;
	}

	public void teleopPeriodic() {
		turbo = controllers.climberHold();
		if (turbo) {
			climber.setSpeed(0.9);
		}
		else {
			if (controllers.climberOn()) {
				climber.setSpeed(.5);
			}
			else {
				climber.setSpeed( -0);
			}
		}
	}
}
