/**
 * 
 */
package input;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.SensorBase;
import robot.IControl;

public class SensorController {

	// All of the sensors for the current robot
	private Map<SensorName, SensorBase> sensors = new HashMap<SensorName, SensorBase>();

	/**
	 * Puts a sensor into the map<br>
	 * Preconditions: the key is a valid SensorName and sensor is valid
	 * SensorBase<br>
	 * Postconditions: adds the sensor<br>
	 * 
	 * @param key
	 *            the sensorName
	 * @param sensor
	 *            the sensor to input
	 */
	public void registerSensor(SensorName key, SensorBase sensor) {
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
	public SensorBase getSensor(SensorName key) {
		return sensors.get(key);
	}
}
