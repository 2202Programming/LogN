package auto;

import drive.IDrive;

public class DriveCommand implements Command {
	private double power;
	private double dist;
	private int currentDist;

	/**
	 * 
	 * @param powIn
	 *            power of the drive
	 * @param distIn
	 *            distance to drive TODO at this point is just number of cycles
	 */
	public DriveCommand(double powIn, double distIn) {
		power=powIn;
		dist=distIn;
	}

	// Runs the Drive command in the way for this robot
	public boolean run(String robotName, IDrive drive) {
		// Use switch when the command must be run differently than the default
		// way
		switch (robotName) {
		default:
			drive.setLeftMotors(power);
			drive.setRightMotors(power);
			currentDist++;
			break;
		}
		return currentDist>=dist;
	}
}
