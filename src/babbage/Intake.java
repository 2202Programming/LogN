package babbage;

import comms.XboxController;
import physicalOutput.IMotor;
import physicalOutput.SparkMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class Intake extends IControl {
	private IMotor[] intakeMotors;
	private BabbageControl controller;
	private double speed=0;
	
	//775 motors
	//2 motors ports 4 and 5
	public Intake() {
		intakeMotors=new SparkMotor[2];
		intakeMotors[0]=new SparkMotor(4, true);
		intakeMotors[1]=new SparkMotor(5, true);
	}
	
	public void robotInit() {
	}

	public void autonomousInit() {
		controller=(BabbageControl) Global.controllers;
	}

	public void autonomousPeriodic() {
		update();
	}
	
	public void teleopInit() {
		controller=(BabbageControl) Global.controllers;
	}

	public void teleopPeriodic() {
		update();
	}
	
	private void update() {
		if (controller.intakeSpeed()) {
			speed=1;
		}
		else {
			speed=.5;
		}
		for (IMotor motor:intakeMotors) {
			motor.setSpeed(speed);
		}
	}
}
