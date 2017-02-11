package auto.stopConditions;

import auto.IStopCondition;
import comms.SmartWriter;

public class TimerStopCondition implements IStopCondition {
	private long startTime;
	private long duration;
	
	/**
	 * Constructs the stop condition
	 * @param durationIn the duration in milliseconds
	 */
	public TimerStopCondition(long durationIn) {
		duration = durationIn;
	}
	
	@Override
	public void init() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public boolean stopNow() {
		SmartWriter.putD("System.currentTimeMillis()", System.currentTimeMillis());
		SmartWriter.putD("start", startTime);
		return (System.currentTimeMillis() - startTime) > duration;
	}


}
