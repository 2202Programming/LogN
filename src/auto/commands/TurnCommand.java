package auto.commands;

import auto.ICommand;
import auto.IStopCondition;
import auto.stopConditions.AngleStopCondition;

public class TurnCommand implements ICommand {
	
	private IStopCondition stopCondition;
	
	public TurnCommand(double degreesToTurn) {
		this(degreesToTurn, 2, 0.3);
	}
	
	public TurnCommand(double degreesToTurn, double marginOfErrorDegrees, double secondsInRange) {
		stopCondition=new AngleStopCondition(degreesToTurn, marginOfErrorDegrees, secondsInRange);
	}
	
	public void init() {
		
	}

	public boolean run() {
		return false;
	}

}
