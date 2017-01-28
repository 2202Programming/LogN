package auto.stopConditions;

import auto.IStopCondition;

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
		return (System.currentTimeMillis() - startTime) > duration;
	}


}
