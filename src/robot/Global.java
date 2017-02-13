package robot;

import java.util.Map;

import robotDefinitions.ControlBase;

/**
 * A class for global variables that are not part of any specific class
 */
public class Global {
	//to get an instance of Sensor or Solenoid Controllers, use SomeController.getInstance()

	//don't set!!!!
	public static Map<String, IControl> controlObjects;
	
	public static ControlBase controllers;
	
	public static ControlBase controlBase;

}