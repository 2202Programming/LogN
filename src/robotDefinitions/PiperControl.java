package robotDefinitions;

import comms.XboxController;

public class PiperControl extends ControlBase {

	public PiperControl() {

	}

	// speeds up intake
	public boolean intakeSpeed() {
		return controllers[0].getAHeld();
	}

	// starts shooting
	public boolean startShooting() {
		return controllers[0].getRightTriggerHeld();
	}

	// stops shooting
	public boolean stopShooting() {
		return controllers[0].getBPressed();
	}

	// reverse shooters, unclogs balls
	public boolean reverseShooter() {
		return controllers[0].getRightBumperHeld();
	}

	// engage high-goal vision
	public boolean hgVision() {
		return controllers[0].getYPressed();
	}

	// cancel high-goal vision
	public boolean cancelHg() {
		return controllers[0].getStartPressed();
	}

	@Override
	public XboxController[] getControllers() {
		XboxController[] controllers = new XboxController[1];
		controllers[0] = XboxController.getXboxController(0);
		return controllers;
	}

}
