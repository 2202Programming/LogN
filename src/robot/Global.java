package robot;

import java.util.Map;

/**
 * A class for global variables that are not part of any specific class
 */
public class Global {
	//to get an instance of Sensor or Solenoid Controllers, use SomeController.getInstance()

	//debugMode was moved to SmartWriter.
	//don't set!!!!
	public static Map<String, IControl> controlObjects;

}