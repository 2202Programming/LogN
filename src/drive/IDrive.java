package drive;

import robot.IControl;

/**
 * This class is for any types of drive we choose to implement in the future
 * @author Daniel
 *
 */
public abstract class IDrive extends IControl {
	//All drive classes must override all of the methods below
	public abstract void teleopInit();
	
	public abstract void teleopPeriodic();
	
	public abstract void setLeftMotors(double speed);
	
	public abstract void setRightMotors(double speed);
	
	public abstract void setEnabled(boolean enabled);
	
	public abstract boolean getEnabled();
	
	public abstract boolean hasEncoders();

}
