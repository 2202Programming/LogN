package auto;

import edu.wpi.first.wpilibj.Encoder;
import input.SensorController;
import robot.IControl;

public class CommandRunner extends IControl {
	private int commandNum;
	private CommandList commands;
	private String robotName;
	private SensorController sensors;

	/**
	 * Constructor for CommandRunner<br>
	 * 
	 * @param xCommands
	 *            The commandList to be run
	 * @param robotName
	 *            Name of the robot for unique commands
	 */
	public CommandRunner(CommandList xCommands, String robotName) {
		commands = xCommands;
		this.robotName = robotName;
		sensors = SensorController.getInstance();
	}

	/**
	 * Resets all of the sensors using the robot name to know which sensors are
	 * active<br>
	 * If there are not sensors to reset, nothing will be done
	 */
	private void resetSensors() {
		switch (robotName) {
		case "TIM":
			((Encoder)sensors.getSensor("FLENCODER")).reset();
			break;
		}
	}

	// starts at the first commandNum
	public void autonomousInit() {
		commandNum = 0;
		resetSensors();
	}

	// runs the command every cycle
	public void autonomousPeriodic() {
		if (commands.getCommand(commandNum).run(robotName)) {
			commandNum++;
			resetSensors();
		}
	}

}
