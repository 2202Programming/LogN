package auto.stopConditions;

import java.util.List;

import auto.IStopCondition;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.Encoder;

public class DistanceStopCondition implements IStopCondition {
	private List<Encoder> enc;
	private int duration;
	
	public DistanceStopCondition(List<Encoder> encoder, int inches) {
		enc = encoder;
		duration = inches;
	}

	public void init() {
		for(Encoder x: enc){
			x.reset();
		}
	}

	public boolean stopNow() {
		int sum = 0;
		for(Encoder x: enc){
			//x.get() returns encoder counts
			//encoder count -> inches will need to be put here
			sum += x.getDistance();
		}
		SmartWriter.putD("AUTO - AVG Encoder Count", sum/enc.size());
		return (sum/enc.size()) > duration;
	}

}
