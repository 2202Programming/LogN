package auto;

import drive.IDrive;

//Interface for Command objects
public interface Command {
	public boolean run(String robotName, IDrive drive);
}
