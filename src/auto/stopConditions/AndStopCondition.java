package auto.stopConditions;

import auto.IStopCondition;

public class AndStopCondition implements IStopCondition {
	IStopCondition first;
	IStopCondition second;

	public AndStopCondition(IStopCondition firstCondition, IStopCondition secondCondition) {
		first = firstCondition;
		second = secondCondition;
	}

	public void init() {
		first.init();
		second.init();
	}

	public boolean stopNow() {
		return first.stopNow() && second.stopNow();
	}
}
