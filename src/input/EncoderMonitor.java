package input;

import java.util.HashMap;
import java.util.Map.Entry;

import comms.DebugMode;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.Encoder;
import robot.IControl;

public class EncoderMonitor extends IControl {

	HashMap<String, Encoder> encoders;
	/**
	 * This class will print the values of all encoders during all modes<br>
	 * Also it will reset during init phases
	 */
	public EncoderMonitor() {
		encoders = new HashMap<String, Encoder>();
	}
	
	public void add(String name, Encoder e){
		encoders.put(name, e);
	}
	
	/**
	 * Print the encoder values to smart dashboard
	 */
	public void teleopPeriodic(){
		for(Entry<String, Encoder> entry : encoders.entrySet()){
			SmartWriter.putD(entry.getKey(), entry.getValue().get(), DebugMode.COMPETITION);
			SmartWriter.putD(entry.getKey() + " DISTANCE", entry.getValue().getDistance(), DebugMode.COMPETITION);
		}
	}
	
	/**
	 * Print the encoder values to smart dashboard
	 */
	public void autonomousPeriodic(){
		for(Entry<String, Encoder> entry : encoders.entrySet()){
			SmartWriter.putD(entry.getKey(), entry.getValue().get(),DebugMode.COMPETITION);
			SmartWriter.putD(entry.getKey() + " DISTANCE", entry.getValue().getDistance(),DebugMode.COMPETITION);
		}
	}
	
	/**
	 * Reset all of the encoders
	 */
	public void teleopInit() {
		for(Entry<String, Encoder> entry : encoders.entrySet()) {
			entry.getValue().reset();
		}
	}
	
	/**
	 * Reset all of the encoders
	 */
	public void autonomousInit() {
		for(Entry<String, Encoder> entry : encoders.entrySet()) {
			entry.getValue().reset();
		}
	}
	
	/**
	 * Reset all of the encoders
	 */
	public void disabledInit() {
		for(Entry<String, Encoder> entry : encoders.entrySet()) {
			entry.getValue().reset();
		}
	}
}
