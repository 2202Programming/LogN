package physicalOutput.motors;

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
	 * Sets the angle of the servo on a scale from -1 to 1
	 * @param x: the angle to set the servo
	 */
	protected void setMotor(double x) {
		part.set(x);
	}
	
	public double getAngle() {
		return part.getAngle();
	}
}
