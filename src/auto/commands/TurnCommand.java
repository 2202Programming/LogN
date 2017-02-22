package auto.commands;

import PID.PIDController;
import PID.PIDValues;
import auto.ICommand;
import auto.stopConditions.AngleStopCondition;
import comms.SmartWriter;
import drive.DriveControl;
import drive.IDrive;
import robot.Global;
import robot.Robot;


public class TurnCommand implements ICommand {
	
	private AngleStopCondition stopCondition;
	private PIDController controller;
	private static PIDValues pidValues;
	private IDrive drive;
	
	
	public TurnCommand(double degreesToTurn) {
		this(new AngleStopCondition(degreesToTurn, 1, 0.3));
	}
	
	public TurnCommand(double degreesToTurn, double maxError, double timeInRange) {
		this(new AngleStopCondition(degreesToTurn, maxError, timeInRange));
	}
	
	
	public TurnCommand(AngleStopCondition stop) {
		stopCondition=stop;
		loadPIDValues();
	}
	
	public void init() {
		controller=new PIDController(pidValues);
		drive=(IDrive)Global.controlObjects.get("DRIVE");
		stopCondition.init();
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
	}

	public boolean run() {
		
		SmartWriter.putD("TurnCommandAngle", stopCondition.getError());
		double motorValue=controller.calculate(0, stopCondition.getError());
		SmartWriter.putD("PID Turning Motor Power", motorValue);
		drive.setLeftMotors(motorValue);
		drive.setRightMotors(-motorValue);
		boolean stopNow=stopCondition.stopNow();
		SmartWriter.putB("hghjkhjghg", stopNow);
		if (stopNow) {
			stop();
			return true;
		}
		return false;
	}
	
	public void stop(){
		drive.setLeftMotors(0);
		drive.setRightMotors(0);
		drive.setDriveControl(DriveControl.DRIVE_CONTROLLED);
	}
	
	private void loadPIDValues() {
		switch(Robot.name) {
		case BABBAGE:
			pidValues=new PIDValues(0.015, 0.0007, .15);
			break;
		case PIPER:
			pidValues=new PIDValues(0.02, 0.0005, 0.15);//new PIDValues(0.005, 0.0002, 0.15);
			break;
		case TIM:
			//TODO setPIDVALUES
			break;
		case UNKNOWN:
			//TODO setPIDVALUES
			break;
		case MECHANUMDRIVE:
			//TODO setPIDVALUES
			break;
		default:
			break;
		
		}
	}

}
