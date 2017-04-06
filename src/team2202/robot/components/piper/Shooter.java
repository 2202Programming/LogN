package team2202.robot.components.piper;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import physicalOutput.motors.ChainMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.PiperControl;

public class Shooter extends IControl{
	private ChainMotor shooterMotorChain;
	private DoubleSolenoid shooterHeight;
	private PiperControl controller;
	
	private boolean _shooterUp;
	private boolean _shooterOn;
	
	public Shooter(ChainMotor shooterMotors,DoubleSolenoid height){
		shooterMotorChain = shooterMotors;
		shooterHeight = height;
		controller = (PiperControl) Global.controllers;
	}
	
	public void teleopInit(){
		shooterHeight.set(DoubleSolenoid.Value.kForward);
		shooterMotorChain.set(0);
		_shooterUp = false;
		_shooterOn = false;
	}
	
	private void getUserInput(){
		_shooterUp = controller.toggleShooterHeight();
		_shooterOn = _shooterOn?controller.stopShooting():controller.startShooting();
	}
	
	public void teleopPeriodic(){
		getUserInput();
		
		if(_shooterUp){
			shooterHeight.set(Value.kForward);
		}else{
			shooterHeight.set(Value.kReverse);
		}
		
		if(_shooterOn){
			shooterMotorChain.set(0.5);
		}else{
			shooterMotorChain.set(0);
		}
	}
}
