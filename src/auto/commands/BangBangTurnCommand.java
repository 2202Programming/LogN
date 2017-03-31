package auto.commands;

import com.kauailabs.navx.frc.AHRS;

import PID.PIDController;
import auto.ICommand;
import auto.IStopCondition;
import drive.DriveControl;
import drive.IDrive;
import input.SensorController;
import robot.Global;
import robotDefinitions.RobotDefinitionBase;

public class BangBangTurnCommand implements ICommand {

	private double power, degreesToTurn;
	private IDrive drive;
	private AHRS navx;
	
	public BangBangTurnCommand(double degreesToTurn, double power) {
		this.degreesToTurn=degreesToTurn;
		this.power=power;
		
	}
	
	public void init() {
		//set the objects and set external control
		drive=(IDrive)Global.controlObjects.get(RobotDefinitionBase.DRIVENAME);
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
		navx=(AHRS)SensorController.getInstance().getSensor("NAVX");
		//reset navx so we turn degrees from when this command is called
		navx.reset();
	}

	public boolean run() {
		double angle=navx.getAngle();
		if (angle>degreesToTurn) {//turn in the right direction
			drive.setLeftMotors(-power);
			drive.setRightMotors(power);			
		}
		else {
			drive.setLeftMotors(power);
			drive.setRightMotors(-power);
		}
		//If we have turned more positive or more negative than our angle, we are done turning. Otherwise, keep going.
		return Math.abs(angle)<Math.abs(degreesToTurn);
	}

	public void stop() {
		drive.setDriveControl(DriveControl.DRIVE_CONTROLLED);
	}

}
