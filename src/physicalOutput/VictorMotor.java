package physicalOutput;
import comms.DebugMode;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.Victor;

//more leinad's stuff

/**
 * Custom IMotor class for the Victor motor controller
 */
public class VictorMotor extends IMotor {

    /**
     * The constructor of a Victor Motor
     * @param port
     * The port that the motor is plugged into
     * @param reverse
     * True if the motor should spin the opposite of the number it is passed
     */
	public VictorMotor(int port,boolean reverse) {
    	super(reverse);
    	part = new Victor(port);
    }

    /**
     * 
     */
    private Victor part;

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
