package auto;

import java.util.ArrayList;

public class CommandList {
	// holds the list of commands to be run
	private ArrayList<Command> commands;

	// default constructor creates an empty command list
	public CommandList() {
		commands=new ArrayList<Command>();
	}

	/**
	 * Adds a command to the list <br>
	 * Preconditions: The command must be a valid Command object <br>
	 * Postconditions: The command is added to the list<br>
	 * 
	 * @param CommandIn
	 *            the command to be inputed
	 */
	public void addCommand(Command CommandIn) {
		commands.add(CommandIn);
	}

	/**
	 * Gets the command at commandNum <br>
	 * Preconditions: commandNum is less or equal to the number of commands <br>
	 * Postconditions: returns the command<br>
	 * 
	 * @param commandNum
	 *            the number of the command
	 * @return the type, power, and distance for the command
	 */
	public Command getCommand(int commandNum) {
		if (commandNum<commands.size()) {
			return commands.get(commandNum);
		}
		return new EmptyCommand();
	}
}
