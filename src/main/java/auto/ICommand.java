package auto;

//Interface for ICommand objects
public interface ICommand {
	/**
	 * initializes the command
	 */
	public void init();
	/**
	 * Runs the command 
	 * @return true if the command is finished
	 */
	public boolean run();
	
	/**
	 * Stops the command
	 */
	public void stop();
}
