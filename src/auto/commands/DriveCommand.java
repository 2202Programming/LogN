package auto.commands;

import auto.ICommand;
import auto.IStopCondition;
import auto.stopConditions.TimerStopCondition;
import drive.DriveControl;
import drive.IDrive;
import robot.Global;

public class DriveCommand implements ICommand {

	private IStopCondition stopCondition;
	private IDrive drive;
	private double speed;
	
	/**
	 * Drives at the constant speed for a given number of seconds at a given motor power
	 * @param secondsToDrive
	 * The number of seconds to drive
	 * @param speed
	 * The speed to drive at, between 0 and 1
	 */
	public DriveCommand(IStopCondition stop, double speed) {
		stopCondition=stop;
		this.speed=speed;
	}
	
	public void init() {
		stopCondition.init();
		drive=(IDrive)Global.controlObjects.get("DRIVE");
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
	}

	public boolean run() {
		drive.setLeftMotors(speed);
		drive.setRightMotors(speed);
		if (stopCondition.stopNow()) {
			drive.setLeftMotors(0);
			drive.setRightMotors(0);
			return true;
		}
		return false;
	}

}
