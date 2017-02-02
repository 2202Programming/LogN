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
		return stopCondition.stopNow();
	}
	
	private void loadPIDValues() {
		switch(Robot.name) {
		case BABBAGE:
			//TODO setPIDVALUES
			break;
		case PIPER:
			//TODO setPIDVALUES
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
