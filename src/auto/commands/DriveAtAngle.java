package auto.commands;

import com.kauailabs.navx.frc.AHRS;

import auto.ICommand;
import auto.IStopCondition;
import comms.SmartWriter;
import drive.DriveControl;
import drive.IDrive;
import input.SensorController;
import robot.Global;

public class DriveAtAngle implements ICommand {

	private IStopCondition stopCondition;
	private IDrive drive;
	private double slowSpeed, fastSpeed;
	private double angle;
	private AHRS navx;
	
	public DriveAtAngle(IStopCondition stop, double slowSpeed, double fastSpeed, double angle) {
		stopCondition=stop;
		this.slowSpeed=slowSpeed;
		this.fastSpeed=fastSpeed;
		navx=(AHRS)(SensorController.getInstance().getSensor("NAVX"));
		this.angle=angle;
	}
	
	public void init() {
		stopCondition.init();
		drive=(IDrive)Global.controlObjects.get("DRIVE");
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
	}

	public boolean run() {
		SmartWriter.putS("TargetAngle driveAtAngle ", getError()+"");
		if (getError()>0) {
			drive.setLeftMotors(slowSpeed);
			drive.setRightMotors(fastSpeed);
		}
		else {
			drive.setLeftMotors(fastSpeed);
			drive.setRightMotors(slowSpeed);
		}
		if (stopCondition.stopNow()) {
			drive.setLeftMotors(0);
			drive.setRightMotors(0);
			drive.setDriveControl(DriveControl.DRIVE_CONTROLLED);
			return true;
		}
		return false;
	}
	
	public double getError() {
		double angle=navx.getAngle();
		if (angle>180) {
			angle=angle-360;
		}
		return angle-this.angle;
	}

}
