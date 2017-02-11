package robotDefinitions;

import comms.XboxController;

/**
 * TODO: Add the implementation for Babbage control methods Add methods for
 * Piper
 */
public class BabbageControl extends ControlBase {

	/**
	 * BabbageControl
	 * 
	 */
	public BabbageControl() {

	}

	/**
	 * gearHolder - places gear on peg
	 * 
	 * @return
	 */
	public boolean toggleGearHolder() {
		return controllers[0].getLeftBumperPressed();
	}

	// speeds up intake
	public boolean intakeSpeed() {
		return controllers[0].getAHeld();
	}

	// starts shooting
	public boolean startShooting() {
		return controllers[1].getRightTriggerHeld();
	}

	// stops shooting
	public boolean stopShooting() {
		return controllers[1].getBPressed();
	}

	// reverse shooters, unclogs balls
	public boolean reverseShooter() {
		return controllers[1].getRightBumperHeld();
	}

	// engage high-goal vision
	public boolean hgVision() {
		return controllers[1].getLeftTriggerPressed();
	}

	// engage gear peg vision
	public boolean gpVision() {
		return controllers[1].getLeftBumperPressed();
	}

	// cancel high-goal vision
	public boolean cancelHg() {
		return controllers[1].getStartPressed();
	}

	// cancel high-goal vision
	public boolean cancelGp() {
		return controllers[1].getBackPressed();
	}

	@Override
	public XboxController[] getControllers() {
		XboxController[] controllers = new XboxController[2];
		controllers[0] = XboxController.getXboxController(0);
		controllers[1] = XboxController.getXboxController(1);
		return controllers;
	}

}
