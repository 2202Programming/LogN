package auto;

import drive.IDrive;
import input.ISensorController;
import robot.Global;

public class TurnCommand implements Command {
	private IDrive drive;
	private double power;
	private double amount;
	private ISensorController sensors;

	/**
	 * Constructs a new TurnCommand
	 * 
	 * @param powIn
	 *            the speed to turn must be between 1.0 and -1.0
	 * @param amountIn
	 *            the amount to turn positive is clockwise, negative is
	 *            counter-clockwise
	 * @param driveIn
	 *            the IDrive object of the robot
	 */
	public TurnCommand(double powIn, double amountIn, IDrive driveIn) {
		drive = driveIn;
		power = powIn;
		amount = amountIn;
		sensors = Global.sensors;
	}

	/**
	 * runs the TurnCommand in the way for this robot
	 */
	public boolean run(String robotName) {
		switch (robotName) {
		default:
			if (amount > 0) {
				drive.setLeftMotors(power);
				drive.setRightMotors( -power);
			}
			else {
				drive.setLeftMotors( -power);
				drive.setRightMotors(power);
			}
			break;
		}
		return false;
	}
}
