package babbage;

import comms.SmartWriter;
import comms.XboxController;
import physicalOutput.IMotor;
import robot.IControl;

public class GearHolder extends IControl {
	private IMotor activator;
	private XboxController controller;
	private final double SPEED = 0.4;
	
	public GearHolder(IMotor motor){
		activator = motor;
		controller = XboxController.getXboxController(1);
	}
	
	public void teleopInit(){
		activator.setSpeed(0);
	}
	
	public void teleopPeriodic(){
		if(controller.getLeftBumperHeld()){
			activator.setSpeed(SPEED);
		}else if(controller.getLeftTriggerHeld()){
			activator.setSpeed(-SPEED);
		}else{
			activator.setSpeed(0);
		}
		SmartWriter.putD("motor7", activator.getSpeed());
	}
}
