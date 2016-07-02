package robot;
import java.util.*;

/**
 * 
 */
public abstract class IMotor extends IControl {

    /**
     * Default constructor
     */
    public IMotor() {
    	switch(Mode)
    	{
    	case Disabled:
    		break;
    	case Enabled:
    		break;
    	}
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