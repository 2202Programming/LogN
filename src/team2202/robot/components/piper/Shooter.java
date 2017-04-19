package team2202.robot.components.piper;

import comms.SmartWriter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import physicalOutput.motors.ChainMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.PiperControl;

public class Shooter extends IControl{
	private ChainMotor shooterMotorChain;
	private DoubleSolenoid shooterHeight;
	private DoubleSolenoid shooterTrigger;
	private PiperControl controller;
	
	private double shooterSpeed=0.4;
	
	private boolean _shooterUp;
	private boolean _shooterOn;
	private boolean _fire;
	
	public Shooter(ChainMotor shooterMotors,DoubleSolenoid height,DoubleSolenoid trigger){
		shooterMotorChain = shooterMotors;
		shooterHeight = height;
		shooterTrigger = trigger;
		controller = (PiperControl) Global.controllers;
	}
	
	public void teleopInit(){
		shooterHeight.set(DoubleSolenoid.Value.kForward);
		shooterTrigger.set(DoubleSolenoid.Value.kForward);
		shooterMotorChain.set(0);
		_shooterUp = false;
		_shooterOn = false;
		_fire = false;
	}
	
	private void getUserInput(){
		_shooterUp ^= controller.toggleShooterHeight();
		
		_fire = _shooterOn?controller.startShooting():false;
		_shooterOn = _shooterOn?!controller.stopShooting():controller.startShooting();
	}
	
	public void teleopPeriodic(){
		getUserInput();
		
		if (controller.getControllers()[0].getStartPressed()) {
			shooterSpeed=Math.min(shooterSpeed+0.05, 0.5);
		}
		if (controller.getControllers()[0].getBackPressed()) {
			shooterSpeed=Math.max(shooterSpeed-0.05, 0.1);
		}
		SmartWriter.putD("***ShooterSpeed:***", shooterSpeed);
		
		if(_shooterUp){
			shooterHeight.set(Value.kForward);
		}else{
			shooterHeight.set(Value.kReverse);
		}
		
		if(_shooterOn){
			shooterMotorChain.set(shooterSpeed);//0.5
		}else{
			shooterMotorChain.set(0);
		}
		
		if(_fire){
			shooterTrigger.set(Value.kReverse);
			_shooterOn = false;
		}else{
			shooterTrigger.set(Value.kForward);
		}
	}
}
