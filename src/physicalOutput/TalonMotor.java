package physicalOutput;
import edu.wpi.first.wpilibj.Talon;


/**
 * 
 */
public class TalonMotor extends IMotor {

    /**
     * Default constructor
     */
	public TalonMotor(int x,boolean reverse) {
    	super(reverse);
    	part = new Talon(x);
    }

    /**
     * 
     */
    private Talon part;

    /**
     * Sets the motor pwm value
     * Preconditions: x must be between -1.0 and 1.0
     * Postconditions: sets the motor pwm value to x
     * @param x
     */
	protected void setMotor(double x) {
		part.set(x);
	}

}