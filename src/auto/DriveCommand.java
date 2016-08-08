package auto;

import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import input.ISensorController;
import input.SensorName;
import other.PIDController;
import robot.Global;

public class DriveCommand implements Command {
	private double power;
	private double dist;
	private int currentDist;
	private IDrive drive;
	private ISensorController sensors;
	private PIDController pidControl;

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
		pidControl = new PIDController();
	}

	// Runs the Drive command in the way for this robot
	public boolean run(String robotName) {
		switch (robotName) {
		case "TIM":
			pidControl.setPID(1, 1, 1);
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
	
	/**
	 * Drive the robot using encoders to keep it straight	
	 * @param main the main encoder that will be used as the set point
	 * @param secondary the secondary encoder that will be keeping up with the main
	 */
	private void encoderDrive(Encoder main, Encoder secondary){
		double change = pidControl.calculate(main.get(), secondary.get());
		//The side that is set should be the same side as the secondary encoder
		drive.setLeftMotors(power - change);
	}
	
	/**
	 * Drive the robot using the gyro sensor to keep it straight<br>
	 * TODO need to set up the libraries for gyro sensors
	 */
	private void gyroDrive(SensorBase gyroSensor){
		double change = pidControl.calculate(0, -1/*TODO put gyro value here*/);
		drive.setLeftMotors(power - change);
		drive.setRightMotors(power + change);
	}
}
