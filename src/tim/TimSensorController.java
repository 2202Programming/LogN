package tim;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import input.ISensorController;

public class TimSensorController extends ISensorController {
	private static TimSensorController controller = null;

	private TimSensorController(){
		super(TimSensorController.registerSensors());
	}
	
	//In place of a constructor so that TimSensorController is a singleton
	//Use this to get an instance of TimSensorController
	public static ISensorController getInstance() {
		if(controller == null){
			controller = new TimSensorController();
		}
		return controller;
	}
	
	//All of the sensors will be put into the Map here
	//TODO Put real sensors into this method
	public static HashMap<String,SensorBase> registerSensors(){
		HashMap<String,SensorBase> temp = new HashMap<String,SensorBase>();
		temp.put("SensorExample", new Encoder(2,3));
		return temp;
	}
}
