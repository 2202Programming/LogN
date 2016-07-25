package auto;

import drive.IDrive;

//Interface for Command objects
public interface Command {
	public boolean run(String robotName, IDrive drive); // TODO Will every command need a IDrive in it, I don't think it will be neccissiary with shooting commands.
}
