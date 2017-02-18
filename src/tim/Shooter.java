package tim;

import comms.DebugMode;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import physicalOutput.IMotor;
import physicalOutput.SolenoidController;
import robot.Global;
import robot.IControl;
import robotDefinitions.TimControl;

public class Shooter extends IControl {
	// 0-disabled 1-enabled 2-external
	public int state;
	private IMotor left;
	private IMotor right;
	private IMotor height;
	private TimControl controller;
	private double curSpeed;
	private double heightSpeed;
	private SolenoidController sc;
	private DoubleSolenoid trigger;

	public Shooter(IMotor left, IMotor right, IMotor height) {
		state = 1;
		this.left = left;
		this.right = right;
		this.height = height;
		controller = (TimControl) Global.controllers;
		sc = SolenoidController.getInstance();
		curSpeed = 0;
		heightSpeed = 0;
		try {
			trigger = sc.getDoubleSolenoid("TRIGGER");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			SmartWriter.putS("SolNcrushed", "Something goes wrong", DebugMode.DEBUG);
		}
	}

	public void autonomousInit() {
		curSpeed = 0;
		heightSpeed = 0;
		setShootRaw(0);
		setHeightRaw(0);
	}

	public void teleopInit() {
		curSpeed = 0;
		heightSpeed = 0;
		setShootRaw(0);
		setHeightRaw(0);
	}

	public void teleopPeriodic() {
		//sets the speed for the shooter wheels Right bumper - faster, Left bumper - slower
		if (controller.stopShooter()) {
			curSpeed = 0;
		}
		else if (controller.speedUpShooter() && curSpeed < 1) {
			curSpeed += .2;
		}
		else if (controller.slowDownShooter() && curSpeed > 0) {
			curSpeed -= .2;
		}
		setShootRaw(curSpeed);

		//Sets the elevation of the shooter Y - up, X - down
		if(controller.shooterHeight()<0){
			heightSpeed = -1.0;
		}else if(controller.shooterHeight()>0){
			heightSpeed = 0.75;
		}else{
			heightSpeed = 0;
		}
		SmartWriter.putD("heightspeed",heightSpeed, DebugMode.DEBUG);
		SmartWriter.putS("motorStatus",height+"", DebugMode.DEBUG);
		setHeightRaw(heightSpeed);
	
		if(controller.shoot())  //Formerly getAHeld() 
		{
			SmartWriter.putB("ABotten", true, DebugMode.DEBUG);
			trigger.set(DoubleSolenoid.Value.kReverse);
		}
		else{
			SmartWriter.putB("ABotten", false, DebugMode.DEBUG);
			trigger.set(DoubleSolenoid.Value.kForward);
		}
		SmartWriter.putS("solState", trigger.get() + " ", DebugMode.DEBUG);
	
	}

	private void setShootRaw(double power) {
		left.setSpeed(power);
		right.setSpeed(power);
	}

	private void setHeightRaw(double speed) {
		height.setSpeed(speed);
	}

	/**
	 * sets both of the shoot motors to power <br>
	 * Preconditions: power is between 1.0 and -1.0 <br>
	 * Postconditions: sets the power
	 * 
	 * @param power
	 */
	public void setShoot(double power) {
		if (state == 2) {
			setShootRaw(power);
		}
	}

	/**
	 * Sets the motor for elevation change positive goes up negative goes down
	 * Preconditions: speed is between 1.0 and -1.0 Postconditions: sets the
	 * motor
	 * 
	 * @param speed
	 */
	public void setHeight(double speed) {
		if (state == 2) {
			setHeightRaw(speed);
		}
	}
}
