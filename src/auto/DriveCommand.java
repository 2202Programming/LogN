package auto;

import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import input.ISensorController;
import input.SensorName;
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
		power = powIn;
		dist = distIn;
		drive = driveIn;
		currentDist = 0;
	}

	// Runs the Drive command in the way for this robot
	public boolean run(String robotName) {
		switch (robotName) {
		case "TIM":
			encoderDrive((Encoder)sensors.getSensor(SensorName.FLENCODER), (Encoder)sensors.getSensor(SensorName.FRENCODER));
			currentDist = ((Encoder)sensors.getSensor(SensorName.FLENCODER)).get();
			break;
		default:
			drive.setLeftMotors(power);
			drive.setRightMotors(power);
			currentDist += 1;
			break;
		}

		return currentDist >= dist;
	}
	
	private void encoderDrive(Encoder main, Encoder secondary){
		//TODO put PID loop here
	}
	
	private void gyroDrive(SensorBase gyroSensor){
		//TODO put PID loop here
	}
}
