package team2202.robot.definitions;

import comms.XboxController;
import physicalOutput.motors.IMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.ControlBase;

public class RunAMotor extends IControl {
	private IMotor motor;
	private ControlBase controller=Global.controllers;
	public RunAMotor(IMotor motor) {
		this.motor = motor;

	}

	public void teleopPeriodic() {
		XboxController xboxcontroller=controller.getControllers()[0];
		if(xboxcontroller.getAHeld())
		{
			double speed=0.2;
			motor.set(speed);
		}
		else
		{
			double speed=0;
			motor.set(speed);
		}
	}

	public void teleopInit() {
		double speed = 0;
		motor.set(speed);
	}
	
	

}
