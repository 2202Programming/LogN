package piper;

import auto.CommandList;
import auto.CommandListRunner;
import auto.commands.TurnCommand;

public class CommandListGear {
	private CommandList commands;
	private CommandListRunner runner;
	
	public CommandListGear() {
		commands = new CommandList();
		
	}
	
	/**
	 * Only call this once when it is suppose to be run
	 */
	public void run() {
		runner=new CommandListRunner(commands);
	}
	
	
}
