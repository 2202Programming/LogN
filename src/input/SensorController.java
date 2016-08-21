/**
 * 
 */
package input;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.SensorBase;

public class SensorController {
	private static SensorController controller;
	// All of the sensors for the current robot
	private Map<String, SensorBase> sensors;

	private SensorController(){
		sensors = new HashMap<String, SensorBase>();
	}
	
	//Use this to get the instance of SensorController
	public static SensorController getInstance(){
		if(controller == null){
			controller = new SensorController();
		}
		return controller;
	}
	/**
	 * Puts a sensor into the map<br>
	 * Preconditions: the key is a valid String and sensor is valid
	 * SensorBase<br>
	 * Postconditions: adds the sensor<br>
	 * 
	 * @param key
	 *            the Sensor's name
	 * @param sensor
	 *            the sensor to input
	 */
	public void registerSensor(String key, SensorBase sensor) {
		sensors.put(key, sensor);
	}

	/**
	 * Gets a sensor from the Map of sensors<br>
	 * Preconditions: The key is tied to a valid sensor<br>
	 * Postconditions: returns the sensor<br>
	 * 
	 * @param key
	 *            the key tied to the desired sensor
	 * @return the sensor that is tied to the inputed key
	 */
	public SensorBase getSensor(String key) {
		return sensors.get(key);
	}
}
