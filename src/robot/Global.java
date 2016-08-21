package robot;

import java.util.Map;

import input.SensorController;
import physicalOutput.SolenoidController;

/**
 * A class for global variables that are not part of any specific class
 */
public class Global {
	//Never set this ever!!! Only the profile can set this field.
	//Use this to get access to all robot sensors
	public static SensorController sensors;
	
	//Use this to get access to all solenoids
	public static SolenoidController solenoids;

	//debugMode was moved to SmartWriter.
	//don't set!!!!
	public static Map<String, IControl> controlObjects;

}