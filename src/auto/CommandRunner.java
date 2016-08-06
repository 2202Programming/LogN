package auto;

import robot.IControl;

public class CommandRunner extends IControl {
	private int commandNum;
	private CommandList commands;
	private String robotName;

	/**
	 * Constructor for CommandRunner<br>
	 * 
	 * @param xCommands
	 *            The commandList to be run
	 * @param robotName
	 *            Name of the robot for unique commands
	 */
	public CommandRunner(CommandList xCommands, String robotName) {
		commands=xCommands;
		this.robotName=robotName;
	}

	// starts at the first commandNum
	public void autonomousInit() {
		commandNum=0;
	}

	// runs the command every cycle
	public void autonomousPerodic() {
		if (commands.getCommand(commandNum).run(robotName)) {
			commandNum++;
		}
	}

}
