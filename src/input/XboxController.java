package input;

import edu.wpi.first.wpilibj.Joystick;
import robot.IControl;

/**
 * A class that will read from Xbox Controller inputs and make the Xbox
 * controller rumble. I will do this eventually --SecondThread
 */
public class XboxController extends IControl {

	private Joystick leftJoystick, rightJoystick;

	/*
	 * These are codes for all the button values so that they can be stored in
	 * an array with these indexes and we don't have 100 different private
	 * variables
	 */
	private final int X_CODE=0;// no one uses XCode
	private final int Y_CODE=1;
	private final int A_CODE=2;
	private final int B_CODE=3;
	private final int START_CODE=4;
	private final int BACK_CODE=5;
	private final int LEFT_BUMBPER_CODE=6;
	private final int RIGHT_BUMBPER_CODE=7;
	private final int L3_CODE=8;
	private final int R3_CODE=9;
	private final int LEFT_TRIGGER_CODE=10;
	private final int RIGHT_TRIGGER_CODE=11;
	
	private int[] debounceCounters=new int[12];

	/**
	 * The singleton instance of this class. <i>xboxController</i> is null if
	 * the singleton has not been instantiated yet.
	 */
	private static XboxController xboxController;

	/**
	 * Gets the singleton xboxController instance. Only one xboxController is
	 * ever created so that different systems get consistent readings. <br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none<br>
	 * 
	 * @return The singleton of this class
	 */
	public static XboxController getXboxController() {
		if (xboxController==null) {
			xboxController=new XboxController();
		}
		return xboxController;
	}

	/**
	 * The default constructor, which creates an xboxController on port 0;
	 */
	private XboxController() {
		this(0);
	}

	/**
	 * The private constructor that sets up the xboxController (preconditions
	 * and postconditions are not required for private methods, doesn't return
	 * anything.
	 */
	private XboxController(int port) {
		leftJoystick=new Joystick(4);
		rightJoystick=new Joystick(4);

		// I don't know what this means or does, but we needed it for the c++
		// version
		leftJoystick.setAxisChannel(Joystick.AxisType.kX, 4);
		leftJoystick.setAxisChannel(Joystick.AxisType.kY, 5);
	}

	/**
	 * Updates the pressed/held values for each of the buttons and joystick
	 * values. This should be called once a frame.<br>
	 * <br>
	 * Preconditions: Called once a frame<br>
	 * Postconditions: any get*Pressed() or get*Released() method will return
	 * true on exactly one frame.
	 */
	private void update() {
		
	}

}
