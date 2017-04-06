package team2202.robot.definitions.controls;

import comms.XboxController;
import robotDefinitions.ControlBase;

public class PiperControl extends ControlBase {

	public PiperControl() {

	}

	// speeds up intake
	public boolean intakeSpeed() {
		return controllers[0].getAHeld();
	}

	// starts shooting
	public boolean startShooting() {
		return controllers[0].getRightTriggerPressed();
	}

	// stops shooting
	public boolean stopShooting() {
		return controllers[0].getBPressed();
	}
	
	//toggles the height of the shooter
	public boolean toggleShooterHeight(){
		return controllers[0].getRightBumperPressed();
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
