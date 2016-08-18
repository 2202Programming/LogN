package tim;

import comms.XboxController;
import physicalOutput.IMotor;
import robot.IControl;

public class Shooter extends IControl {
	// 0-disabled 1-enabled 2-external
	public int state;
	private IMotor left;
	private IMotor right;
	private IMotor height;
	private XboxController controller;

	public Shooter(IMotor left, IMotor right, IMotor height) {
		state = 1;
		this.left = left;
		this.right = right;
		this.height = height;
		controller = XboxController.getXboxController();
	}
	
	public void teleopInit(){
		setShootRaw(0);
		setHeightRaw(0);
	}
	
	public void teleopPeriodic(){
		if(true){
			setShootRaw(1);
		}
	}
	
	private void setShootRaw(double power){
		left.setSpeed(power);
		right.setSpeed(power);
	}

	private void setHeightRaw(double speed){
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
	 * Preconditions: speed is between 1.0 and -1.0
	 * Postconditions: sets the motor
	 * @param speed
	 */
	public void setHeight(double speed){
		if(state == 2){
			setHeightRaw(speed);
		}
	}
}
