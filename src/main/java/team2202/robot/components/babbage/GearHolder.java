package team2202.robot.components.babbage;

import comms.SmartWriter;
import physicalOutput.motors.IMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.BabbageControl;

public class GearHolder extends IControl {
	private IMotor activator;
	private BabbageControl controller;
	private final double SPEED = 0.5;
	private boolean isDown;
	
	public GearHolder(IMotor motor){
		activator = motor;
		controller = (BabbageControl) Global.controllers;
		isDown = false;
	}
	
	public void teleopInit(){
		activator.set(0);
	}
	
	public void teleopPeriodic(){
		if(controller.toggleGearHolder()){
			isDown = !isDown;
		}
		if(isDown){
			activator.set(SPEED);
		}else{
			activator.set(-SPEED);
		}
		SmartWriter.putD("gearMotor", activator.getSpeed());
	}
}
