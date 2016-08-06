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
		currentDist = 0;
	}
	
	// Runs the Drive command in the way for this robot
	public boolean run(String robotName) {
		switch(robotName){
		case "TIM":
			break;
		default:
			pwrCalc(false,false);
			calcDist(false);
			break;
		}
		return currentDist>=dist;
	}

	/**
	 * sets the power of the motors with correction if sensors are available
	 * @param gyro does the current robot have a gyro sensor
	 * @param encoders does the current robot have encoders on both sides
	 */
	private void pwrCalc(boolean gyro, boolean encoders) {
		if (gyro) {

		}else if(encoders){
			
		}
		drive.setLeftMotors(power);
		drive.setRightMotors(power);
	}

	/**
	 * Calculates the distance the robot has driven each cycle Defaults to amount of cycles
	 * @param encoders does the current robot have encoders
	 */
	private void calcDist(boolean encoders) {
		if (encoders) {
			
		}else{
			currentDist+=1;
		}
	}
}
