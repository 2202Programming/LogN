package piper;

import auto.CommandList;
import auto.CommandListRunner;
import auto.commands.DriveCommand;
import auto.commands.TurnCommand;
import auto.stopConditions.TimerStopCondition;

public class CommandListRunnerDoNotKeepItSucks{
	private CommandList commands;
	private CommandListRunner runner;
	public CommandListRunnerDoNotKeepItSucks(){
		commands = new CommandList();
		commands.addCommand(new DriveCommand(new TimerStopCondition(2000),0.5));
		commands.addCommand(new TurnCommand(90));
		runner = new CommandListRunner(commands);
	}
	

}
