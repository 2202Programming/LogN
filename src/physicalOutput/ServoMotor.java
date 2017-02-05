package physicalOutput;

import edu.wpi.first.wpilibj.Servo;

public class ServoMotor extends IMotor {

	private Servo part;
	private double minAngle;
	private double maxAngle;
	
	public ServoMotor(int port){
		part = new Servo(port);
	}
	
	public ServoMotor(int port, double minAngle, double maxAngle){
		this(port);
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
	}
	
	/**
	 * Sets the motor's angle from min to max
	 * @param x: the angle to set the servo
	 */
	protected void setMotor(double x) {
		part.setAngle(Math.max(minAngle, x%maxAngle));
	}
}
