package input;

import robot.IControl;

/**
 * A class that will read from Xbox Controller inputs and make the Xbox
 * controller rumble. I will do this eventually --SecondThread
 */
public class XboxController extends IControl {
	/**
	 * The singleton instance of this class. <i>xboxController</i> is null if
	 * the singleton has not been instantiated yet.
	 */
	private static XboxController xboxController;

	/**
	 * Gets the singleton xboxController instance. Only one xboxController is
	 * ever created so that different systems get consistent readings.
	 * <br>
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
	 * The private constructor that sets up the xboxController
	 * (preconditions and postconditions are not required for private methods, doesn't return anything.
	 */
	private XboxController() {

	}

}
