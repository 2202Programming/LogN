package robotDefinitions;

import comms.XboxController;
import robot.IControl;

public abstract class ControlBase extends IControl {

	protected XboxController[] controllers;

	public ControlBase() {
		controllers = getControllers();
	}

	public abstract XboxController[] getControllers();

	// turns left and right
	public double getLeftJoystickX() {
		return controllers[0].getLeftJoystickX();
	}

	// foward and reverse
	public double getLeftJoystickY() {
		return controllers[0].getLeftJoystickY();
	}

}
