package physicalOutput;

import edu.wpi.first.wpilibj.Spark;

//more leinad's stuff

/**
 * 
 */
public class SparkMotor extends IMotor {

	/**
	 * Default constructor
	 */
	public SparkMotor(int port, boolean reverse) {
		super(reverse);
		part=new Spark(port);
	}

	private Spark part;

	/**
	 * This is only used by IMotor
	 * 
	 * Sets the motor pwm value Preconditions: x must be between -1.0 and 1.0
	 * Postconditions: sets the motor pwm value to x
	 * 
	 * @param x
	 */
	protected void setMotor(double x) {
		part.set(x);
	}

}