package tim;

import physicalOutput.IMotor;
import robot.IControl;

public class Shooter extends IControl {
	// 0-disabled 1-enabled 2-external
	public int state;
	private IMotor left;
	private IMotor right;
	private IMotor height;

	public Shooter(IMotor left, IMotor right, IMotor height) {
		state = 1;
		this.left = left;
		this.right = right;
		this.height = height;
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
			left.setSpeed(power);
			right.setSpeed(power);
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
			height.setSpeed(speed);
		}
	}
}
