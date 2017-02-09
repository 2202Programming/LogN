package babbage;

import comms.SmartWriter;
import physicalOutput.IMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class GearHolder extends IControl {
	private IMotor activator;
	private BabbageControl controller;
	private final double SPEED = 0.5;
	private boolean isDown;
	
	public GearHolder(IMotor motor){
		activator = motor;
		controller = (BabbageControl) Global.controlObjects.get("CONTROL");
		isDown = false;
	}
	
	public void teleopInit(){
		activator.setSpeed(0);
	}
	
	public void teleopPeriodic(){
		if(controller.toggleGearHolder()){
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
