package input;

import java.util.HashMap;
import java.util.Map.Entry;

import comms.SmartWriter;
import edu.wpi.first.wpilibj.Encoder;
import robot.IControl;

public class EncoderMonitor extends IControl {

	HashMap<String, Encoder> encoders;
	
	public void add(String name, Encoder e){
		encoders.put(name, e);
	}
	
	public void teleopPeriodic(){
		for(Entry<String, Encoder> entry : encoders.entrySet()){
			SmartWriter.putD(entry.getKey(), entry.getValue().get());
		}
	}
	
}
