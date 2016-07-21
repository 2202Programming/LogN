package auto;

import drive.ArcadeDrive;

//Interface for Command objects
public interface Command {
	public boolean run(String robotName, ArcadeDrive drive);
}
