package auto;

public class TimCommand extends ICommand {
	public TimCommand(CommandList xCommands){
		super(xCommands);
	}
	
	/**
	 * Runs the command for the Tim robot
	 * Preconditions: command must be a valid command
	 * Postconditions: command will be run
	 */
	public boolean runCommand(double[] command){
		//see the CommandName enum for what each command is
		//TODO Need to implement
		switch((int)command[0]){
		case 0: 
		}
		return false;
	}
}
