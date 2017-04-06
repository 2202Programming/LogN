package team2202.robot.components.piper;

import physicalOutput.SolenoidController;
import physicalOutput.motors.IMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.PiperControl;

public class Intake extends IControl {
	
	private boolean intakeOpen;
	private IMotor motor;
	private SolenoidController solenoidController;
	private PiperControl controlBase;
	
	public Intake(IMotor motor) {
		this.motor=motor;
		this.solenoidController=SolenoidController.getInstance();
		controlBase=(PiperControl) Global.controllers;
	}
	
	public void teleopInit() {
		intakeOpen=false;
	}
	
	public void teleopPeriodic() {
		intakeOpen=controlBase.intakeSpeed();
		if (intakeOpen) {
			motor.set(1);
			try {
				solenoidController.getSolenoid("intakeSolenoid").set(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			motor.set(0);
			try {
				solenoidController.getSolenoid("intakeSolenoid").set(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
