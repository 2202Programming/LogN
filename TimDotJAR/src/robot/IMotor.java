package robot;

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
    	case ENABLED:
    		break;
    	case DISABLED:
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
    protected abstract void setMotor(double x);

    /**
     * 
     */
    public void teleopInit() {
        // TODO implement here
    }

    /**
     * 
     */
    public void teleopPeriodic() {
        // TODO implement here
    }

    /**
     * 
     */
    public void autoInit() {
        // TODO implement here
    }

    /**
     * 
     */
    public void autoPeridic() {
        // TODO implement here
    }

}