package babbage;

import physicalOutput.IMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class Climber extends IControl {
	private IMotor climber;
	private boolean started;
	private BabbageControl controllers;
	private int counter;
	
	public Climber(IMotor motor){
		climber = motor;
		started = false;
		controllers = (BabbageControl) Global.controllers;
	}
	
	public void teleopInit(){
		climber.setSpeed(0);
		started = false;
		counter = 0;
	}
	
	public void teleopPeriodic(){
		System.out.println(controllers.climberOn());
		if(controllers.climberOn()){
			started = true;
			climber.setSpeed(0.9);
		}else{
			if(started){
				climber.setSpeed(-0.2);
			}
		}
		
		if(controllers.climberHold()){
			climber.setSpeed(0.05);
		}
	}
}
