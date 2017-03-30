package team2202.robot.components.hoenheim;

import physicalOutput.motors.IMotor;
import robot.IControl;

public class Intake extends IControl {
	private IMotor motor;
	private int isOn;

	public Intake(IMotor motor) {
		this.motor = motor;
	}

	/**
	 * Sets the intake to intake, off, or reverse<br>
	 * Preconditions: isOn is one of the three stated values<br>
	 * Postconditions: if not, the intake will not work
	 * 
	 * @param isOn
	 *            1: intake, 0: off, -1: reverse
	 */
	public void setOn(int isOn) {
		this.isOn = isOn;
	}

	public void teleopInit() {
		isOn = 0;
	}

	public void teleopPeriodic() {
		motor.set(isOn);
	}
}
