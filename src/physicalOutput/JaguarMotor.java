package physicalOutput;
import comms.DebugMode;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.Jaguar;


/**
 * 
 */
public class JaguarMotor extends IMotor {

    /**
     * Default constructor
     */
    public JaguarMotor(int x) {
    	part = new Jaguar(x);
    }

    /**
     * 
     */
    private Jaguar part;

    /**
     * Sets the motor pwm value
     * Preconditions: x must be between -1.0 and 1.0
     * Postconditions: sets the motor pwm value to x
     * @param x
     */
	protected void setMotor(double x) {
		SmartWriter.putD("Motorstuff", x, DebugMode.FULL);
		part.set(x);
	}

}