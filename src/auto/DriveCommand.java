package auto;

import drive.IDrive;
import input.ISensorController;
import robot.Global;

public class DriveCommand implements Command {
	private double power;
	private double dist;
	private int currentDist;
	private IDrive drive;
	private ISensorController sensors;

	/**
	 * 
	 * @param powIn
	 *            power of the drive
	 * @param distIn
	 *            distance to drive TODO at this point is just number of cycles
	 */
	public DriveCommand(double powIn, double distIn, IDrive driveIn) {
		sensors = Global.sensors;
		power=powIn;
		dist=distIn;
		drive = driveIn;
	}
	
	// Runs the Drive command in the way for this robot
	public boolean run(String robotName) {
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
		//TODO check for encoders
		if (true) {
			
		}
		return 1;
	}
}
