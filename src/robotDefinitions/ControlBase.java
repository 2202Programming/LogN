package robotDefinitions;

import comms.XboxController;
import robot.IControl;

public abstract class ControlBase extends IControl {

	protected XboxController[] controllers;

	public ControlBase() {
		controllers = getControllers();
	}

	public abstract XboxController[] getControllers();

}
