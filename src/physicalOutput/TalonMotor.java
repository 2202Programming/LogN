package physicalOutput;
import edu.wpi.first.wpilibj.Talon;


/**
 * Custom IMotor class for the Talon motor controller
 */
public class TalonMotor extends IMotor {

    /**
     * The constructor of a Talon motor
     * @param port
     * The port that the motor is plugged into
     * @param reverse
     * True if the motor should spin the opposite of the number it is passed
     */
	public TalonMotor(int port,boolean reverse) {
    	super(reverse);
    	part = new Talon(port);
    }

    /**
     * 
     */
    private Talon part;

    /**
     * This is only run by IMotor, people call setSpeed in IMotor instead
     * 
     * Sets the motor pwm value
     * Preconditions: x must be between -1.0 and 1.0
     * Postconditions: sets the motor pwm value to x
     * @param x
     */
	protected void setMotor(double x) {
		part.set(x);
	}

}
