package babbage;

import comms.SmartWriter;
import comms.XboxController;
import physicalOutput.IMotor;
import robot.IControl;

public class GearHolder extends IControl {
	private IMotor activator;
	private XboxController controller;
	private final double SPEED = 0.4;
	private boolean isDown;
	
	public GearHolder(IMotor motor){
		activator = motor;
		controller = XboxController.getXboxController(1);
		isDown = false;
	}
	
	public void teleopInit(){
		activator.setSpeed(0);
	}
	
	public void teleopPeriodic(){
		if(controller.getLeftBumperPressed()){
			isDown = !isDown;
		}
		if(isDown){
			activator.setSpeed(SPEED);
		}else{
			activator.setSpeed(-SPEED);
		}
		SmartWriter.putD("gearMotor", activator.getSpeed());
	}
}
