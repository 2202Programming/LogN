package auto;

public interface IStopCondition {
	/**
	 * Initializes the stop condition
	 */
	public void init();
	/**
	 * checks if the stop condition is met
	 * @return true if done
	 */
	public boolean stopNow();
}
