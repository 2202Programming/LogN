package robot;
import edu.wpi.first.wpilibj.Encoder;

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
    public IMotor motor;

    /**
     * 
     */
    public Encoder encoder;

    /**
     * 
     */
    public MotorControlMode controlMode;

    /**
     * 
     */
    public double Speed;

    /**
     * @param x
     */
    public void SetSpeed(double x) {
        // TODO implement here
    }

}