package motors;
import edu.wpi.first.wpilibj.Encoder;
import robot.IControl;

/**
 * 
 */
public class SCMotor extends IControl {

    /**
     * Default constructor
     */
    public SCMotor() {
    }

    /**
     * 
     */
    private IMotor motor;

    /**
     * 
     */
    private Encoder encoder;

    /**
     * 
     */
    private boolean enabled;

    /**
     * 
     */
    private double speed;

    /**
     * @param x
     */
    public void SetSpeed(double x) {
        // TODO implement here
    }

}