package auto;

import java.util.ArrayList;

public class CommandList {
	// These hold the command type, power, and distances of each command
	private ArrayList<CommandName> commands;
	private ArrayList<Double> powers;
	private ArrayList<Double> distances;

	// default constructor
	public CommandList() {
		commands = new ArrayList<CommandName>();
		powers = new ArrayList<Double>();
		distances = new ArrayList<Double>();
	}

	/**
	 * Adds a command to the list Preconditions: The type must be a valid
	 * commandName. Power is between -1.0 and 1.0 Postconditions: The command is
	 * added to the list
	 * 
	 * @param type
	 *            the type of command to be inputed
	 * @param power
	 *            the power for the command
	 * @param dist
	 *            the distance or angle for command
	 */
	public void addCommand(CommandName type, double power, double dist) {
		commands.add(type);
		powers.add(power);
		distances.add(dist);
	}

	// For adding a command that does not require power or distance ex: shoot
	public void addCommand(CommandName type) {
		commands.add(type);
		powers.add(0.0);
		distances.add(0.0);
	}

	/**
	 * Gets the command at commandNum Preconditions: commandNum is less or equal
	 * to the number of commands Postconditions: returns the command
	 * 
	 * @param commandNum
	 *            the number of the command
	 * @return the type, power, and distance for the command
	 */
	public double[] getCommand(int commandNum) {
		if (commandNum < commands.size()) {
			double[] toReturn = new double[3];
			toReturn[0] = commands.get(commandNum).getValue();
			toReturn[1] = powers.get(commandNum);
			toReturn[2] = distances.get(commandNum);
			return toReturn;
		}
		return new double[] { 0, 0, 0 };
	}

}
