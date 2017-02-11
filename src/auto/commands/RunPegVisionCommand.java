package auto.commands;

import java.util.ArrayList;

import auto.ICommand;
import auto.stopConditions.DistanceStopCondition;
import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import edu.wpi.first.wpilibj.Encoder;
import input.SensorController;
import robot.Global;

public class RunPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private boolean doneWithVision=false;
	private ArrayList<ICommand> subcommands=new ArrayList<>();
	private double percentToFinish=0;
	
	public RunPegVisionCommand(double percentToFinish) {
		table=new NetworkTables(TableNamesEnum.VisionTable);
		this.percentToFinish=percentToFinish;
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
				SmartWriter.putS("Peg Vision State", "Done, but I shouldn't be here");
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
						SmartWriter.putS("Peg Vision State", "Done");
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
			
			distanceToMove*=percentToFinish;
			
			distanceToMove-=20;
			//<temp>
			//distanceToMove=1;
			//</temp>
			//4- DO 6 and 7
			//3- DO 4 and 5
			//2- DO 2 and 3
			//1- DO 0 and 1
			ArrayList<Encoder> encoders=new ArrayList<>();
			encoders.add((Encoder)SensorController.getInstance().getSensor("ENCODER1"));
			subcommands.add(new DriveCommand(new DistanceStopCondition(encoders, (int)distanceToMove), .5));//TODO, make this work on
			return false;
		}
	}
	
}
