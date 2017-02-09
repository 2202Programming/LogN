package babbage;

import comms.SmartWriter;
import physicalOutput.IMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.ControlBase;

public class GearHolder extends IControl {
	private IMotor activator;
	private ControlBase controller;
	private final double SPEED = 0.5;
	private boolean isDown;
	
	public GearHolder(IMotor motor){
		activator = motor;
		controller = Global.controlObjects.get("CONTROL");
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
