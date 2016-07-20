package auto;

import robot.IControl;

public class ICommand extends IControl {
	private int commandNum;
	private CommandList commands;
	
	/**
	 * Constructor 
	 * @param xCommands the CommandList to be executed
	 */
	public ICommand(CommandList xCommands) {
		commands = xCommands;
	}
	
	//starts at the first commandNum
	public void autonomousInit() {
		commandNum = 0;
	}

	//runs the command every cycle
	public void autonomousPerodic() {
		if (runCommand(commands.getCommand(commandNum))) {
			commandNum++;
		}
	}
	
	//This method should be overwritten with the Command runner of the robot
	public boolean runCommand(double[] command) {
		return false;
	}
}
