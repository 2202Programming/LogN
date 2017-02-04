package auto.commands;

import auto.CommandList;
import auto.CommandListRunner;
import auto.ICommand;
import auto.stopConditions.TimerStopCondition;
import comms.NetworkTables;
import comms.TableNamesEnum;

public class RunPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private boolean doneWithVision=false;
	private CommandList moveAndTurnList;
	private CommandListRunner runner;
	
	public RunPegVisionCommand() {
		table=new NetworkTables(TableNamesEnum.VisionTable);
	}

	public void init() {
		table.setBoolean("processVision", true);
	}

	public boolean run() {
		if (doneWithVision) {
			if (runner.runList()) {
				return true;
			}
		}
		if (table.getBoolean("processVision")) {
			//vision is still running
			return false;
		}
		else {
			//vision is done
			degreesToTurn=table.getDouble("degreesToTurn");
			distanceToMove=table.getDouble("distanceToMove");
			doneWithVision=true;
			moveAndTurnList=new CommandList();
			moveAndTurnList.addCommand(new TurnCommand(degreesToTurn, 1.0, .5));
			moveAndTurnList.addCommand(new DriveCommand(new TimerStopCondition((long)distanceToMove), .5));//TODO, make this work on
			runner=new CommandListRunner(moveAndTurnList);
			runner.setCallAutomatically(false);
			return false;
		}
	}
}
