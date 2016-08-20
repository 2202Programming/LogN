package auto;

import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import input.SensorController;
import other.PIDController;
import robot.Global;

public class TurnCommand implements Command {
	private IDrive drive;
	private double power;
	private double amount;
	private SensorController sensors;
	private PIDController pidControl;

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
		pidControl = new PIDController();
	}

	/**
	 * runs the TurnCommand in the way for this robot
	 */
	public boolean run(String robotName) {
		switch (robotName) {
		default:
			if (amount > 0) {
				amount--;
				drive.setLeftMotors(power);
				drive.setRightMotors( -power);
			}
			else if (amount < 0) {
				amount++;
				drive.setLeftMotors( -power);
				drive.setRightMotors(power);
			}
			break;
		}
		return amount == 0;
	}
	
	/**
	 * Turn the robot using a gyro sensor for amount
	 * @param gyroSensor
	 */
	public void gyroTurn(SensorBase gyroSensor){
		//TODO put an actual gyro value into input
		double change = pidControl.calculate(amount, -1);
		//TODO finish implementation
	}
	
	/**
	 * Turn the robot using Encoders to keep track of how far each side has gone
	 * @param left Encoder for the left side of the robot
	 * @param right Encoder for the Right side of the robot
	 */
	public void EncoderTurn(Encoder left, Encoder right){
		//TODO implement here
	}
}
