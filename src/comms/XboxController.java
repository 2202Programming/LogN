package comms;

import edu.wpi.first.wpilibj.Joystick;
import robot.IControl;

/**
 * A class that will read from Xbox Controller inputs and make the Xbox
 * controller rumble. I will do this eventually --SecondThread
 */
public class XboxController extends IControl {
	private String _port;
	
	private Joystick leftJoystick, rightJoystick;
	private boolean teehee = true;

	/**
	 * These are all magic number that the FRC toolchain uses. For some reason
	 * they appear to be indexed from 1, idk why.
	 */
	private final int BUTTON_A_WPICODE = 1;
	private final int BUTTON_B_WPICODE = 2;
	private final int BUTTON_X_WPICODE = 3;
	private final int BUTTON_Y_WPICODE = 4;
	private final int BUTTON_LB_WPICODE = 5;
	private final int BUTTON_RB_WPICODE = 6;
	private final int BUTTON_BACK_WPICODE = 7;
	private final int BUTTON_START_WPICODE = 8;
	private final int BUTTON_L3_WPICODE = 9;
	private final int BUTTON_R3_WPICODE = 10;

	private final int AXIS_TRIGGER_LEFT_WIPCODE = 2;// for left trigger and
	private final int AXIS_TRIGGER_RIGHT_WIPCODE = 3;// right trigger
														// respectively

	private final int AXIS_RIGHT_X_WIPCODE = 4;// for joysticks
	private final int AXIS_RIGHT_Y_WIPCODE = 5;
	private final int AXIS_LEFT_X_WIPCODE = 0;
	private final int AXIS_LEFT_Y_WIPCODE = 1;

	/*
	 * These are codes for all the button values so that they can be stored in
	 * an array with these indexes and we don't have 100 different private
	 * variables
	 */
	private final int X_CODE = 0;// no one uses Xcode, Atom is where it's at
	private final int Y_CODE = 1;
	private final int A_CODE = 2;
	private final int B_CODE = 3;
	private final int START_CODE = 4;
	private final int BACK_CODE = 5;
	private final int LEFT_BUMPER_CODE = 6;
	private final int RIGHT_BUMPER_CODE = 7;
	private final int L3_CODE = 8;
	private final int R3_CODE = 9;
	private final int LEFT_TRIGGER_CODE = 10;
	private final int RIGHT_TRIGGER_CODE = 11;

	private final int NUMBER_OF_BUTTONS = 12;
	private final int maxDebounceCounter = 3;
	private int[] debounceCounters = new int[NUMBER_OF_BUTTONS];
	private boolean[] thisFrame = new boolean[NUMBER_OF_BUTTONS];
	private boolean[] lastFrame = new boolean[NUMBER_OF_BUTTONS];
	private boolean[] pressed = new boolean[NUMBER_OF_BUTTONS];
	private boolean[] held = new boolean[NUMBER_OF_BUTTONS];
	private boolean[] released = new boolean[NUMBER_OF_BUTTONS];

	/**
	 * The singleton instance of this class. <i>xboxController</i> is null if
	 * the singleton has not been instantiated yet.
	 */
	private static XboxController[] xboxController = new XboxController[4];

	public static XboxController getXboxController(){
		return getXboxController(0);
	}
	/**
	 * Gets the singleton xboxController instance. Only one xboxController is
	 * ever created so that different systems get consistent readings. <br>
	 * <br>
	 * Preconditions: none<br>
	 * Postconditions: none<br>
	 * 
	 * @return The singleton of this class
	 */
	public static XboxController getXboxController(int port) {
		if (port <= 3) {
			if (xboxController[port] == null) {
				xboxController[port] = new XboxController(port);
			}
			return xboxController[port];
		}
		return null;
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
		_port = port + "";
		leftJoystick = new Joystick(port);
		rightJoystick = new Joystick(port);

		// I don't know what this means or does, but we needed it for the c++
		// version
		leftJoystick.setAxisChannel(Joystick.AxisType.kX, 4);
		rightJoystick.setAxisChannel(Joystick.AxisType.kY, 5);
		if (teehee) {
			setRumble(0.1);
		}
	}

	// calls the update method
	public void teleopPeriodic() {
		update();
		SmartWriter.putD("StickY", getLeftJoystickY(), DebugMode.DEBUG);
		SmartWriter.putD("StickX", getLeftJoystickX(), DebugMode.DEBUG);
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
		updateButton(X_CODE, rightJoystick.getRawButton(BUTTON_X_WPICODE));
		updateButton(Y_CODE, rightJoystick.getRawButton(BUTTON_Y_WPICODE));
		updateButton(A_CODE, rightJoystick.getRawButton(BUTTON_A_WPICODE));
		updateButton(B_CODE, rightJoystick.getRawButton(BUTTON_B_WPICODE));
		updateButton(START_CODE, rightJoystick.getRawButton(BUTTON_START_WPICODE));
		updateButton(BACK_CODE, rightJoystick.getRawButton(BUTTON_BACK_WPICODE));
		updateButton(LEFT_BUMPER_CODE, rightJoystick.getRawButton(BUTTON_LB_WPICODE));
		updateButton(RIGHT_BUMPER_CODE, rightJoystick.getRawButton(BUTTON_RB_WPICODE));
		updateButton(L3_CODE, rightJoystick.getRawButton(BUTTON_L3_WPICODE));
		updateButton(R3_CODE, rightJoystick.getRawButton(BUTTON_R3_WPICODE));
		updateButton(LEFT_TRIGGER_CODE, rightJoystick.getRawAxis(AXIS_TRIGGER_LEFT_WIPCODE) > 0.8);
		updateButton(RIGHT_TRIGGER_CODE, rightJoystick.getRawAxis(AXIS_TRIGGER_RIGHT_WIPCODE) > 0.8);
	}

	/**
	 * Updates the debounceCounter, state of the button for this frame and last
	 * frame, pressed, held, and released values for the give button code. This
	 * should be called every frame.
	 * 
	 * @param buttonCode
	 * @param currentlyDown
	 */
	private void updateButton(int buttonCode, boolean currentlyDown) {
		SmartWriter.putB(buttonCode +" " + _port, currentlyDown, DebugMode.FULL);
		lastFrame[buttonCode] = thisFrame[buttonCode];
		if ( !currentlyDown) {
			debounceCounters[buttonCode] = 0;
			thisFrame[buttonCode] = false;
		}
		else {
			debounceCounters[buttonCode]++;
			if (debounceCounters[buttonCode] >= maxDebounceCounter) {
				debounceCounters[buttonCode] = maxDebounceCounter;
				thisFrame[buttonCode] = true;
			}
			else {
				thisFrame[buttonCode] = false;
			}
		}
		pressed[buttonCode] = thisFrame[buttonCode] && !lastFrame[buttonCode];
		held[buttonCode] = thisFrame[buttonCode] && lastFrame[buttonCode];
		released[buttonCode] = !thisFrame[buttonCode] && lastFrame[buttonCode];
	}

	public double getRightJoystickX() {
		return rightJoystick.getRawAxis(AXIS_RIGHT_X_WIPCODE);
	}

	public double getRightJoystickY() {
		return ( -1.0) * rightJoystick.getRawAxis(AXIS_RIGHT_Y_WIPCODE);
	}

	public double getLeftJoystickX() {
		return leftJoystick.getRawAxis(AXIS_LEFT_X_WIPCODE);
	}

	public double getLeftJoystickY() {
		return ( -1.0) * leftJoystick.getRawAxis(AXIS_LEFT_Y_WIPCODE);
	}

	public boolean getXPressed() {
		return pressed[X_CODE];
	}

	public boolean getXHeld() {
		return held[X_CODE];
	}

	public boolean getXReleased() {
		return released[X_CODE];
	}

	public boolean getYPressed() {
		return pressed[Y_CODE];
	}

	public boolean getYHeld() {
		return held[Y_CODE];
	}

	public boolean getYReleased() {
		return released[Y_CODE];
	}

	public boolean getAPressed() {
		return pressed[A_CODE];
	}

	public boolean getAHeld() {
		return held[A_CODE];
	}

	public boolean getAReleased() {
		return released[A_CODE];
	}

	public boolean getBPressed() {
		return pressed[B_CODE];
	}

	public boolean getBHeld() {
		return held[B_CODE];
	}

	public boolean getBReleased() {
		return released[B_CODE];
	}

	public boolean getStartPressed() {
		return pressed[START_CODE];
	}

	public boolean getStartHeld() {
		return held[START_CODE];
	}

	public boolean getStartReleased() {
		return released[START_CODE];
	}

	public boolean getBackPressed() {
		return pressed[BACK_CODE];
	}

	public boolean getBackHeld() {
		return held[BACK_CODE];
	}

	public boolean getBackReleased() {
		return released[BACK_CODE];
	}

	public boolean getLeftBumperPressed() {
		return pressed[LEFT_BUMPER_CODE];
	}

	public boolean getLeftBumperHeld() {
		return held[LEFT_BUMPER_CODE];
	}

	public boolean getLeftBumperReleased() {
		return released[LEFT_BUMPER_CODE];
	}

	public boolean getRightBumperPressed() {
		return pressed[RIGHT_BUMPER_CODE];
	}

	public boolean getRightBumperHeld() {
		return held[RIGHT_BUMPER_CODE];
	}

	public boolean getRightBumperReleased() {
		return released[RIGHT_BUMPER_CODE];
	}

	public boolean getL3Pressed() {
		return pressed[L3_CODE];
	}

	public boolean getL3Held() {
		return held[L3_CODE];
	}

	public boolean getL3Released() {
		return released[L3_CODE];
	}

	public boolean getR3Pressed() {
		return pressed[R3_CODE];
	}

	public boolean getR3Held() {
		return held[R3_CODE];
	}

	public boolean getR3Released() {
		return released[R3_CODE];
	}

	public boolean getLeftTriggerPressed() {
		return pressed[LEFT_TRIGGER_CODE];
	}

	public boolean getLeftTriggerHeld() {
		return held[LEFT_TRIGGER_CODE];
	}

	public boolean getLeftTriggerReleased() {
		return released[LEFT_TRIGGER_CODE];
	}

	public boolean getRightTriggerPressed() {
		return pressed[RIGHT_TRIGGER_CODE];
	}

	public boolean getRightTriggerHeld() {
		return held[RIGHT_TRIGGER_CODE];
	}

	public boolean getRightTriggerReleased() {
		return released[RIGHT_TRIGGER_CODE];
	}

	public void setRumble(boolean shouldRumble) {// of course it should!
		setRumble(shouldRumble?1:0);
	}

	public void setRumble(double rumblyness) {
		float radness = (float)rumblyness;
		rightJoystick.setRumble(Joystick.RumbleType.kLeftRumble, radness);
		rightJoystick.setRumble(Joystick.RumbleType.kRightRumble, radness);
		leftJoystick.setRumble(Joystick.RumbleType.kLeftRumble, radness);
		leftJoystick.setRumble(Joystick.RumbleType.kRightRumble, radness);
	}

}
