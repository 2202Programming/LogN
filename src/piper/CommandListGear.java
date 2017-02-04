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
		run();
	}
	
	/**
	 * Only call this once when it is suppose to be run
	 */
	public void run() {
		runner=new CommandListRunner(commands);
	}
	
	
}
