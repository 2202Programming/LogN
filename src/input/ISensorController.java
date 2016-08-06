/**
 * 
 */
package input;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.SensorBase;
import robot.IControl;

/**
 * @author Daniel
 * An ISensorController class cannot be instantiated, but each type of sensor controller extends it
 */
public class ISensorController extends IControl{
	
	//All of the sensors for the current robot
	private Map<SensorName,SensorBase> sensors;
	
	//Called by the SensorController of each robot
	protected ISensorController(HashMap<SensorName,SensorBase> sensors){
		this.sensors = sensors;
	}
	
	/**
	 * Gets a sensor from the Map of sensors
	 * Preconditions: The key is tied to a valid sensor
	 * Postconditions: returns the sensor
	 * @param key the key tied to the desired sensor
	 * @return the sensor that is tied to the inputed key
	 */
	public SensorBase getSensor(SensorName key){
		return sensors.get(key);
	}
}
