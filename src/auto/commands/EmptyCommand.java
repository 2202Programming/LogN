package auto.commands;

import auto.ICommand;
import auto.IStopCondition;

public class EmptyCommand implements ICommand {
	private IStopCondition stop;

	public EmptyCommand(IStopCondition stopCondition) {
		stop = stopCondition;
	}
	
	@Override
	public void init() {
		stop.init();
	}
	
	@Override
	public boolean run() {
		return stop.stopNow();
	}
}
