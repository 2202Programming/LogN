package auto.commands;

import auto.ICommand;
import babbage.Shooter;
import robot.Global;

public class ShootCommand implements ICommand {
	private Shooter shooter;
	@Override
	public void init() {
		shooter = (Shooter) Global.controlObjects.get("SHOOTER");
		shooter.setAutoShoot(true);
	}

	@Override
	public boolean run() {
		
		return false;
	}

	@Override
	public void stop() {
		shooter.setAutoShoot(false);
	}

}
