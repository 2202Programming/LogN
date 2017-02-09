package auto.commands;

import java.util.ArrayList;

import auto.CommandList;
import auto.CommandListRunner;
import auto.ICommand;
import auto.stopConditions.TimerStopCondition;
import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private boolean doneWithVision=false;
	private ArrayList<ICommand> subcommands=new ArrayList<>();
	
	
	public RunPegVisionCommand() {
		table=new NetworkTables(TableNamesEnum.VisionTable);
	}

	public void init() {
		SmartWriter.putD("Peg Vision activates", System.currentTimeMillis());
		doneWithVision=false;
		subcommands.clear();
		table.setBoolean("processVision", true);
	}

	public boolean run() {
		if (doneWithVision) {
			if (subcommands.isEmpty()) {
				return true;
			}
			else {
				SmartWriter.putS("Peg Vision State", "Turning/Driveing");
				if (subcommands.get(0).run()) {
					subcommands.remove(0);
					if (!subcommands.isEmpty()) {
						subcommands.get(0).init();
					}
					else {
						return true;
					}
				}
				return false;
			}
		}
		if (table.getBoolean("processVision")) {
			SmartWriter.putS("Peg Vision State", "Visioning");
			//vision is still running
			return false;
		}
		else {
			//vision is done
			degreesToTurn=table.getDouble("degreesToTurn");
			distanceToMove=table.getDouble("distanceToMove");
			SmartWriter.putD("degreesToTurn final", degreesToTurn);
			SmartWriter.putD("distanceToMove final", distanceToMove);
			doneWithVision=true;
			subcommands.add(new TurnCommand(degreesToTurn, 1, .5));
			subcommands.get(0).init();
			//subcommands.add(new DriveCommand(new TimerStopCondition((long)distanceToMove), .5));//TODO, make this work on
			return false;
		}
	}
	
}
