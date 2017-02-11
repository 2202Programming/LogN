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
		for(Encoder e : enc){
		}
	}
	@Override
	public void init() {
		for(Encoder x: enc){
			x.reset();
		}
	}

	@Override
	public boolean stopNow() {
		int sum = 0;
		for(Encoder x: enc){
			//x.get() returns encoder counts
			//encoder count -> inches will need to be put here
			Encoder encoder = new Encoder(0, 1);
			sum += x.get();
		}
		SmartWriter.putD("Encoder Count", sum);
		return (sum/enc.size()) > duration;
	}

}
