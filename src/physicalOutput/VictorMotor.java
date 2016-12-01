package physicalOutput;
import edu.wpi.first.wpilibj.Victor;

//more leinad's stuff

/**
 * 
 */
public class VictorMotor extends IMotor {

    /**
     * Default constructor
     */
	public VictorMotor(int x,boolean reverse) {
    	super(reverse);
    	part = new Victor(x);
    }

    /**
     * 
     */
    private Victor part;

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