package team2202.robot.definitions.controls;

import comms.XboxController;
import robotDefinitions.ControlBase;

public class TimControl extends ControlBase {
	public TimControl() {

	}
	
	public boolean stopShooter(){
		return controllers[0].getBHeld();
	}

	/** speedUpShooter - Speeds up the shooter
	 * 
	 * @return
	 */
	public boolean speedUpShooter() {
		return controllers[0].getRightTriggerHeld();
	}
	
	/** slowDownShooter - Slows down the shooter
	 * 
	 * @return
	 */
	public boolean slowDownShooter(){
		return controllers[0].getLeftTriggerHeld();
	}
	
	/** shoot - Shoots a frisbee
	 * 
	 * @return
	 */
	public boolean shoot(){
		return controllers[0].getBPressed();
	}
	
	/**shooterHeight - Adjusts the height of the shooter
	 * 
	 * @return
	 */
	public double shooterHeight(){
		return controllers[0].getRightJoystickY();
	}

	@Override
	public XboxController[] getControllers() {
		XboxController[] controllers = new XboxController[1];
		controllers[0] = XboxController.getXboxController(0);
		return controllers;
	}

}

