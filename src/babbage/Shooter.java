package babbage;

import comms.XboxController;
import physicalOutput.IMotor;
import robot.IControl;

public class Shooter extends IControl
{
	private IMotor[] shooterMotors;
	private XboxController controller;
	private double speed = 0;
	
	//1 motor port ?? 
	public Shooter(IMotor[] motors)
	{
		shooterMotors = motors;
		controller = XboxController.getXboxController();
	}
	
	public void teleopInit()
	{
		for(IMotor i : shooterMotors)
			i.setSpeed(0);
	}
	
	public void teleopPeriodic()
	{
		if(controller.getAHeld())
		{
			for(IMotor i : shooterMotors)
				i.setSpeed(.5);
		}
		else
		{
			for(IMotor i : shooterMotors)
				i.setSpeed(0);
		}
	}
	
}
