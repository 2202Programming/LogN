package robot;

import java.util.ArrayList;
import java.util.List;

//done --SecondThread

/**
 * An abstract class that is to be implemented by all control objects. It allows
 * the objects to be notified when init and periodic are called in teleop, auto,
 * and disabled, as well as robotInit. When extending this class, you do not
 * need to implement all of the methods, but if you want functionality for any
 * of them, they should be overridden.
 */
public abstract class IControl {

	/**
	 * A list of all created IControl objects which is used in update and init methods. IControl objects automatically
	 */
	private static List<IControl> allObjects=new ArrayList<IControl>();
	
	/**
	 * Default constructor for all IControls 
	 */
	public IControl() {
		allObjects.add(this);
	}
	
	/**
	 * The first method called in a competition or as soon as code is deployed
	 * to the robot in practice when it has connection to the drivers station.
	 * Use this for initializing objects when the constructor cannot be used.
	 * <br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none
	 */
	public void robotInit() {
		
	}

	/**
	 * Called in the beginning of the teleoperated period. <br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none
	 */
	public void teleopInit() {
	}

	/**
	 * Called approximately every 5 milliseconds (~200 times per second) in
	 * teleop. This could change depending on how long each cycle takes, but
	 * should not be called more than 200 times each second.<br>
	 * <br>
	 * Preconditions: TeleopInit is called before this<br>
	 * Postconditions: none
	 */
	public void teleopPeriodic() {
	}

	/**
	 * Called in the beginning of the autonomous period. Note that
	 * teleopInit/Periodic is not necessarily called after auto. Also, there is
	 * no time limit in auto when not in competitions<br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none
	 */
	public void autonomousInit() {
	}

	/**
	 * Called approximately every 5 milliseconds (~200 times per second) in
	 * auto. This could change depending on how long each cycle takes, but
	 * should not be called more than 200 times each second.<br>
	 * <br>
	 * Preconditions: autonomousInit is called before this<br>
	 * Postconditions: none
	 */
	public void autonomousPeriodic() {
	}

	/**
	 * Called as soon as the robot is disabled.<br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none
	 */
	public void disabledInit() {
	}

	/**
	 * Called approximately every 5 milliseconds (~200 times per second) when
	 * the robot is disabled. This could change depending on how long each cycle
	 * takes, but should not be called more than 200 times each second.<br>
	 * <br>
	 * Preconditions: disabledInit is called before this<br>
	 * Postconditions: none
	 */
	public void disabledPeriodic() {
	}
	
	public static void callRobotInit() {
		for (int i=0; i<allObjects.size(); i++) {			
			allObjects.get(i).robotInit();
		}
	}
	
	public static void callAutonomousInit() {
		for (int i=0; i<allObjects.size(); i++) {			
			allObjects.get(i).autonomousInit();
		}
	}
	
	public static void callAutonomousPeriodic() {
		for (IControl i:allObjects) {
			i.autonomousPeriodic();
		}
	}
	
	public static void callTeleopInit() {
		for (IControl i:allObjects) {
			i.teleopInit();
		}
	}
	
	public static void callTeleopPeriodic() {
		for (IControl i:allObjects) {
			i.teleopPeriodic();
		}
	}
	
	public static void callDisabledInit() {
		for (IControl i:allObjects) {
			i.disabledInit();
		}
	}
	
	public static void callDisabledPeriodic() {
		for (IControl i:allObjects) {
			i.disabledPeriodic();
		}
	}

}