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
		return controllers[0].getRightBumperHeld();
	}

	// speeds up intake
	public boolean intakeSpeed() {
		return controllers[0].getAHeld();
	}

	public boolean intakeEngaged() {
		return controllers[2].getAHeld();
	}

	// starts shooting
	public boolean startShooting() {
		return controllers[1].getRightTriggerHeld();
	}

	// reverse shooters, unclogs balls
	public boolean reverseShooter() {
		return controllers[1].getRightBumperHeld();
	}

	// engage high-goal vision
	public boolean startHighGoalVision() {
		return controllers[1].getLeftTriggerPressed();
	}

	// engage gear peg vision
	public boolean startPegVision() {
		return controllers[0].getLeftBumperPressed();
	}

	/**
	 * cancel high-goal vision
	 * 
	 * @return
	 */
	public boolean cancelHighGoalVision() {
		return controllers[1].getStartPressed();
	}

	/**
	 * cancel Peg vision
	 * 
	 * @return
	 */
	public boolean cancelPegVision() {
		return controllers[0].getStartPressed();
	}

	/**
	 * 
	 * @return
	 */
	public boolean climberOn() {
		return controllers[2].getBHeld();
	}

	/**
	 * 
	 * @return
	 */
	public boolean cimberHold() {
		return controllers[2].getXHeld();
	}

	public boolean autoFieldPosition0() {//Aren't we doing this through Smart Dashboard?
		return controllers[2].getYHeld();
	}

	public boolean autoFieldPosition1() {
		return controllers[2].getLeftBumperHeld();
	}

	public boolean autoFieldPosition2() {
		return controllers[2].getRightBumperHeld();
	}

	public boolean autoShooter() {
		return controllers[2].getBackHeld();
	}

	public boolean autoGear() {
		return controllers[2].getStartHeld();
	}

	public boolean leftJoystickPress() {
		return controllers[2].getL3Held();
	}

	public boolean rightJoystickPress() {
		return controllers[2].getR3Held();
	}

	@Override
	public XboxController[] getControllers() {
		XboxController[] controllers = new XboxController[3];
		controllers[0] = XboxController.getXboxController(0);
		controllers[1] = XboxController.getXboxController(1);
		controllers[2] = XboxController.getXboxController(2);
		return controllers;
	}

}
