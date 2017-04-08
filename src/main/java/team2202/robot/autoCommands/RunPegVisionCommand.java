package team2202.robot.autoCommands;

import java.util.ArrayList;

import auto.CommandList;
import auto.CommandListRunner;
import auto.ICommand;
import auto.commands.DriveCommand;
import auto.commands.TurnCommand;
import auto.stopConditions.DistanceStopCondition;
import comms.DebugMode;
import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import drive.DriveControl;
import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import input.SensorController;
import robot.Global;
import robotDefinitions.RobotDefinitionBase;
import team2202.robot.definitions.controls.BabbageControl;

public class RunPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private boolean doneWithVision=false;
	private CommandListRunner runner;
	private double percentToFinish=0;
	
	public RunPegVisionCommand(double percentToFinish) {
		table=new NetworkTables(TableNamesEnum.VisionTable);
		this.percentToFinish=percentToFinish;
	}

	public void init() {
		SmartWriter.putD("Peg Vision activates", System.currentTimeMillis());
		doneWithVision=false;
		runner=null;
		table.setBoolean("processVision", true);
		IDrive drive=((IDrive)Global.controlObjects.get(RobotDefinitionBase.DRIVENAME));
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
		drive.setLeftMotors(0);
		drive.setRightMotors(0);
	}

	public boolean run() {
		if (((BabbageControl)(Global.controllers)).cancelPegVision()) {
			stop();
			return true;
		}
		if (doneWithVision) {
			return runner.runList();
		}
		
		if (table.getBoolean("processVision")) {
			SmartWriter.putS("Peg Vision State", "Visioning");
			return false;
		}
		else {
			degreesToTurn=table.getDouble("degreesToTurn");
			distanceToMove=table.getDouble("distanceToMove");
			SmartWriter.putS("Peg Vision Results", "Angle: "+degreesToTurn+", Distance: "+distanceToMove, DebugMode.COMPETITION);
			doneWithVision=true;
			
			CommandList list=new CommandList();
			list.addCommand(new TurnCommand(degreesToTurn, 1, .5));
			
			distanceToMove-=5;
			distanceToMove*=percentToFinish;
			
			ArrayList<Encoder> encoders=new ArrayList<>();
			encoders.add((Encoder)SensorController.getInstance().getSensor("ENCODER0"));
			list.addCommand(new DriveCommand(new DistanceStopCondition(encoders, (int)distanceToMove), .3));
			runner=new CommandListRunner(list);
			runner.init();
			return false;
		}
	}
	
	public void stop(){
		runner.stop();
	}
}
