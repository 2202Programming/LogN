package drive;

import motors.IMotor;
import robot.IControl;

/**
 * @author lazar
 * @category Drive
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
	private boolean enabled;

	/**
	 * 
	 */
	private IMotor frontRight;

	/**
	 * 
	 */
	private IMotor frontLeft;

	/**
	 * 
	 */
	private IMotor backRight;

	/**
	 * 
	 */
	private IMotor backLeft;

	/**
	 * @param fl
	 *            The front left motor
	 * @param fr
	 *            The front right motor
	 * @param bl
	 *            The back left motor
	 * @param br
	 *            The back right motor
	 */
	public ArcadeDrive(IMotor fl, IMotor fr, IMotor bl, IMotor br) {
		frontLeft=fl;
		frontRight=fr;
		backLeft=bl;
		backRight=br;
	}

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
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled=enabled;
	}

	/**
	 * @return bool
	 */
	public boolean getEnabled() {
		return enabled;
	}

}