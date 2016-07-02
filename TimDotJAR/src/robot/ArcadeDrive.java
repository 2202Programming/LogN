
import java.util.*;

/**
 * 
 */
public class ArcadeDrive extends IControl {

    /**
     * Default constructor
     */
    public ArcadeDrive() {
    }

    /**
     * 
     */
    private boolean Enabled;

    /**
     * 
     */
    private IMotor FrontRight;

    /**
     * 
     */
    private IMotor FrontLeft;

    /**
     * 
     */
    private IMotor BackRight;

    /**
     * 
     */
    private IMotor BackLeft;

    /**
     * @param FL 
     * @param FR 
     * @param RL 
     * @param RR
     */
    public void ArcadeDrive(IMotor FL, IMotor FR, IMotor RL, IMotor RR) {
        // TODO implement here
    }

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
     * @param x
     */
    public void setEnabled(boolean x) {
        // TODO implement here
    }

    /**
     * @return
     */
    public boolean getEnabled() {
        // TODO implement here
        return false;
    }

}