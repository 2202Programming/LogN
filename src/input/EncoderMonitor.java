package input;

import java.util.HashMap;
import java.util.Map.Entry;

import comms.SmartWriter;
import edu.wpi.first.wpilibj.Encoder;
import robot.IControl;

public class EncoderMonitor extends IControl {

	HashMap<String, Encoder> encoders;
	
	public EncoderMonitor() {
		encoders = new HashMap<String, Encoder>();
	}
	
	public void add(String name, Encoder e){
		encoders.put(name, e);
	}
	
	public void teleopPeriodic(){
		for(Entry<String, Encoder> entry : encoders.entrySet()){
			SmartWriter.putD(entry.getKey(), entry.getValue().get());
			SmartWriter.putD(entry.getKey() + " DISTANCE", entry.getValue().getDistance());
		}
	}
	
	public void autonomousPeriodic(){
		for(Entry<String, Encoder> entry : encoders.entrySet()){
			SmartWriter.putD(entry.getKey(), entry.getValue().get());
			SmartWriter.putD(entry.getKey() + " DISTANCE", entry.getValue().getDistance());
		}
	}
	
	public void teleopInit() {
		for(Entry<String, Encoder> entry : encoders.entrySet()) {
			entry.getValue().reset();
		}
	}
	
	public void autonomousInit() {
		for(Entry<String, Encoder> entry : encoders.entrySet()) {
			entry.getValue().reset();
		}
	}
	
	public void disabledInit() {
		for(Entry<String, Encoder> entry : encoders.entrySet()) {
			entry.getValue().reset();
		}
	}
}
