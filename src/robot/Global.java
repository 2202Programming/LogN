package robot;

import input.ISensorController;

/**
 * A class for global variables that are not part of any specific class
 */
public class Global {
	//Never set this ever!!! Only the profile can set this field.
	//Use this to get access to all robot sensors
	public static ISensorController sensors;

	//debugMode was moved to SmartWriter.

}