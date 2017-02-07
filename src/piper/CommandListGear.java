package piper;

import auto.CommandList;
import auto.CommandListRunner;
import auto.commands.RunPegVisionCommand;

public class CommandListGear {
	private CommandList commands;
	private CommandListRunner runner;
	
	public CommandListGear() {
		commands = new CommandList();
		commands.addCommand(new RunPegVisionCommand());
		runner=new CommandListRunner(commands);
	}	
	
}
