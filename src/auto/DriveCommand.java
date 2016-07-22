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
		drive.setLeftMotors(pwrCalc(false));
		drive.setRightMotors(pwrCalc(false));
		dist+=calcDist(drive);
		
		return currentDist>=dist;
	}

	private double pwrCalc(boolean gyro) {
		if (gyro) {

		}
		return power;
	}

	private double calcDist(IDrive drive) {
		if (drive.hasEncoders()) {
			
		}
		return 1;
	}
}
