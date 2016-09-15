package auto;

//Interface for Command objects
public interface Command {
	public boolean run(String robotName); // TODO Will every command need a IDrive in it, I don't think it will be neccissiary with shooting commands.
}
