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
 *
 */
public class ISensorController extends IControl{
	
	private Map<String,SensorBase> sensors;
	protected ISensorController(HashMap<String,SensorBase> sensors){
		this.sensors = sensors;
	}
	
	public SensorBase getSensor(String key){
		return sensors.get(key);
	}
}
