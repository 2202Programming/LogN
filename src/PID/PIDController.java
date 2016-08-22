package PID;

/**
 * 
 * A lightweight general purpose PID controller
 * 
 * @author David and Daniel
 *
 */
public class PIDController {
	private double kp;
	private double ki;
	private double kd;
	private double totalError;
	private double lastError;
	private boolean resetOnOvershoot;

	/**
	 * A default constructor for a PIDController<br>
	 * Must set pid values before using calculate
	 */
	public PIDController() {
		this(0, 0, 0, true);
	}
	
	

	/**
	 * A basic PIDController for random stuff(Can be improved greatly)
	 * 
	 * @param kp
	 *            Proportional Constant
	 * @param ki
	 *            Integral Constant
	 * @param kd
	 *            Derivative Constant
	 * @param resetOnOvershoot
	 *            Sets whether the I value should be reset when it is overshot
	 *            (Use if you aren't using d)
	 */
	public PIDController(double kp, double ki, double kd, boolean resetOnOvershoot) {
		this.kp=kp;
		this.ki=ki;
		this.kd=kd;
		totalError=0;
		lastError=0;
		this.resetOnOvershoot=resetOnOvershoot;
	}

	/**
	 * Sets new values for the pid variables
	 * 
	 * @param kp
	 * The new P constant
	 * @param ki
	 * The new I constant
	 * @param kd
	 * The new D constant
	 */
	public void setPID(double kp, double ki, double kd) {
		this.kp=kp;
		this.ki=ki;
		this.kd=kd;
	}

	/**
	 * calculates the output using the PIDController
	 * 
	 * @param targetValue
	 *            The desired number
	 * @param currentValue
	 *            the current value of whatever you're using (Gyro, Encoder,
	 *            etc.)
	 * @return The output using the p, i, and d values set in the constructor
	 */
	public double calculate(double targetValue, double currentValue) {
		

		double error=currentValue-targetValue;

		//reset if I should on overshoot, and I overshot
		if (resetOnOvershoot&&Math.signum(error)!=Math.signum(lastError)) {
			totalError=0;
		}

		totalError+=error;
		double pChange=-kp*error;//If my error is negative, spin motors faster
		double iChange=-ki*totalError;
		double dChange=-kd*(error-lastError);//(y2-y1)/(x2-x1)*constant
		double output=pChange+iChange+dChange;
		lastError=error;
		
		return output;
	}
}
