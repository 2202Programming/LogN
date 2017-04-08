package auto.commands;

import auto.ICommand;
import robot.Global;
import team2202.robot.components.babbage.Shooter;

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
