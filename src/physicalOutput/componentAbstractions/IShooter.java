package physicalOutput.componentAbstractions;

/**
 * An interface for all shooters that we have<br>
 * By using this, we will be able to abstract things like autonomous shooting.
 * 
 * @author Daniel
 *
 */
public interface IShooter {
	/**
	 * Tells the shooter to shoot whatever it shoots
	 * 
	 * @param shouldShoot
	 *            if the shooter should shoot or not
	 * @return true if the shooter accepted the command, false otherwise.
	 */
	public boolean setShoot(boolean shouldShoot);

	/**
	 * Tells if the shooter is currently shooting
	 * 
	 * @return if it is shooting
	 */
	public boolean isShooting();
}
