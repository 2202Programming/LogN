package auto.commands;

import auto.ICommand;
import auto.IStopCondition;

public class WaitCommand implements ICommand {
	IStopCondition stop;
	
	public WaitCommand(IStopCondition stop) {
		this.stop = stop;
	}

	@Override
	public void init() {
		stop.init();
	}

	@Override
	public boolean run() {
		return stop.stopNow();
	}

	@Override
	public void stop() {
	}

}
