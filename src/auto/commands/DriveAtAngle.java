package auto.commands;

import com.kauailabs.navx.frc.AHRS;

import PID.PIDController;
import PID.PIDValues;
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
	private PIDController controller;
	private boolean usePID;

	/**
	 * Creates the command using pid controlled angle
	 * 
	 * @param stop
	 * @param speed
	 * @param angle
	 */
	public DriveAtAngle(IStopCondition stop, double speed, double angle) {
		usePID = true;
		// these will most likely be small as the value needs to be under 1.0/
		// -1.0
		controller = new PIDController(0.01, 0.00005, .01, true, false);
		stopCondition = stop;
		this.angle = angle;
		slowSpeed = speed;
	}

	/**
	 * Creates the command that waddles back and forth
	 * 
	 * @param stop
	 * @param slowSpeed
	 * @param fastSpeed
	 * @param angle
	 */
	public DriveAtAngle(IStopCondition stop, double slowSpeed, double fastSpeed, double angle) {
		usePID = false;
		stopCondition = stop;
		this.slowSpeed = slowSpeed;
		this.fastSpeed = fastSpeed;
		this.angle = angle;
	}
	
	public void setSpeed(double speed) {
		this.slowSpeed=speed;
	}
	
	public void setSpeed(double slowSpeed, double fastSpeed) {
		this.slowSpeed=slowSpeed;
		this.fastSpeed=fastSpeed;
	}

	public void init() {
		stopCondition.init();
		navx = (AHRS) (SensorController.getInstance().getSensor("NAVX"));
		navx.reset();
		drive = (IDrive) Global.controlObjects.get("DRIVE");
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
		
	}

	public boolean run() {
		SmartWriter.putS("TargetAngle driveAtAngle ", getError() + ", NavXAngle: "+navx.getAngle());
		if (usePID) {
			withGyro();
		} else {
			nonGyro();
		}

		if (stopCondition.stopNow()) {
			stop();
			return true;
		}
		return false;
	}


	private void withGyro() {
		double change = controller.calculate(0, getError());
		drive.setLeftMotors(slowSpeed - change);
		drive.setRightMotors(slowSpeed + change);
	}

	private void nonGyro() {
		if (getError() > 0) {
			drive.setLeftMotors(slowSpeed);
			drive.setRightMotors(fastSpeed);
		} else {
			drive.setLeftMotors(fastSpeed);
			drive.setRightMotors(slowSpeed);
		}
	}

	/**
	 * Returns the angle off the set point from -180 to 180
	 * 
	 * @return error
	 */
	public double getError() {
		return this.angle-getAngle();
	}
	
	public double getAngle() {
		double angle = navx.getAngle();
		if (angle > 180) {
			angle = angle - 360;
		}
		return angle;
	}

	/**
	 * sets the new angle for the robot to drive to<br>
	 * must be -180 to 180 at this point
	 */
	public void setAngle(double angleIn) {
		angle = angleIn;
	}

	public void stop() {
		drive.setLeftMotors(0);
		drive.setRightMotors(0);
		drive.setDriveControl(DriveControl.DRIVE_CONTROLLED);
	}
}
