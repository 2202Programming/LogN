
import java.util.*;

/**
 * 
 */
public abstract class IMotor extends IControl {

    /**
     * Default constructor
     */
    public IMotor() {
    }

    /**
     * 
     */
    public MotorControlMode Mode;

    /**
     * 
     */
    public double setValue;


    /**
     * @param x
     */
    protected abstract void SetMotor(double x);

    /**
     * 
     */
    public void TeleopInit() {
        // TODO implement here
    }

    /**
     * 
     */
    public void TeleopPeriodic() {
        // TODO implement here
    }

    /**
     * 
     */
    public void AutoInit() {
        // TODO implement here
    }

    /**
     * 
     */
    public void AutoPeridic() {
        // TODO implement here
    }

}