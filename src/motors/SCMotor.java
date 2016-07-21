package motors;
import edu.wpi.first.wpilibj.Encoder;
import robot.IControl;

//more leinad's stuff

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
     * Constructor for encoder controlled motor
     * @param motorIn motor to be added
     * @param encoderIn encoder to be added
     */
    public SCMotor(IMotor motorIn, Encoder encoderIn){
    	motor = motorIn;
    	encoder = encoderIn;
    }
    /**
     * @param speed
     */
    public void SetSpeed(double speed) {
        // TODO implement here
    }

}