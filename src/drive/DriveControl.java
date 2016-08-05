package drive;

/**
 * Stores the state of an IDrive. The motors can either be controlled by the
 * IDrive (drive_controlled), be disabled, or be controlled by some other part
 * of the robot (external_control)
 * 
 * @author SecondThread
 */
public enum DriveControl {
	DRIVE_CONTROLLED, DISABLED, EXTERNAL_CONTROL
}
