package auto.commands;

import PID.PIDController;
import PID.PIDValues;
import auto.ICommand;
import auto.stopConditions.AngleStopCondition;
import drive.IDrive;
import robot.Global;
import robot.Robot;


public class TurnCommand implements ICommand {
	
	private AngleStopCondition stopCondition;
	private PIDController controller;
	private static PIDValues pidValues;
	private IDrive drive;
	
	public TurnCommand(double degreesToTurn) {
		this(degreesToTurn, 2, 0.3);
	}
	
	public TurnCommand(double degreesToTurn, double marginOfErrorDegrees, double secondsInRange) {
		stopCondition=new AngleStopCondition(degreesToTurn, marginOfErrorDegrees, secondsInRange);
		loadPIDValues();
	}
	
	public void init() {
		controller=new PIDController(pidValues);
		drive=(IDrive)Global.controlObjects.get("DRIVE");
	}

	public boolean run() {
		double motorValue=controller.calculate(0, stopCondition.getError());
		drive.setLeftMotors(motorValue);
		drive.setRightMotors(-motorValue);
		if (stopCondition.stopNow()) {
			drive.setLeftMotors(0);
			drive.setRightMotors(0);
			return true;
		}
		return false;
	}
	
	private void loadPIDValues() {
		switch(Robot.name) {
		case BABBAGE:
			//TODO setPIDVALUES
			break;
		case PIPER:
			pidValues=new PIDValues(0.01, 0.0005, 0.4);
			break;
		case TIM:
			//TODO setPIDVALUES
			break;
		case UNKNOWN:
			//TODO setPIDVALUES
			break;
		
		}
	}

}
