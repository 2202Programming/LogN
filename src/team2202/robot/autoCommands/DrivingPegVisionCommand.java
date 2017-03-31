package team2202.robot.autoCommands;

import java.util.ArrayList;

import auto.CommandList;
import auto.CommandListRunner;
import auto.ICommand;
import auto.commands.DriveAtAngle;
import auto.commands.TurnCommand;
import auto.stopConditions.DistanceStopCondition;
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

public class DrivingPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private boolean doneWithVision=false;
	private CommandListRunner runner=null;
	private double percentToFinish=0;
	private ArrayList<Encoder> encoders;
	private IDrive drive;
	private float speed=0;

	public DrivingPegVisionCommand(double percentToFinish, float speed) {
		table=new NetworkTables(TableNamesEnum.VisionTable);
		this.percentToFinish=percentToFinish;
		this.speed=speed;
	}

	public void init() {
		SmartWriter.putD("Peg Vision activated", System.currentTimeMillis());
		doneWithVision=false;
		runner=null;
		table.setBoolean("processVision", true);
		drive=(IDrive)Global.controlObjects.get(RobotDefinitionBase.DRIVENAME);
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
		drive.setLeftMotors(speed);
		drive.setRightMotors(speed);

		encoders=new ArrayList<>();
		encoders.add((Encoder)SensorController.getInstance().getSensor("ENCODER0"));
		encoders.get(0).reset();
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
			// vision is done
			// ((ArcadeDrive)drive).setReverse(true);
			degreesToTurn=table.getDouble("degreesToTurn");
			distanceToMove=table.getDouble("distanceToMove");
			SmartWriter.putD("degreesToTurn final", degreesToTurn);
			SmartWriter.putD("distanceToMove final", distanceToMove);
			doneWithVision=true;


			double distanceTraveled=encoders.get(0).getDistance();
			double distanceX=Math.cos(Math.toRadians(degreesToTurn))*distanceTraveled;
			double distanceY=Math.sin(Math.toRadians(degreesToTurn))*distanceTraveled;
			double increasedAngle=Math.toDegrees(Math.atan2(distanceY, distanceToMove-distanceX));
			degreesToTurn+=increasedAngle;
			distanceToMove=Math.hypot(distanceToMove-distanceX, distanceY);

			distanceToMove*=percentToFinish;

			float slowPower=speed-0.1f;
			float fastPower=speed+0.1f;

			CommandList list=new CommandList();
			list.addCommand(new TurnCommand(degreesToTurn, 0.5, .001));

			ArrayList<Encoder> encoders=new ArrayList<>();
			encoders.add((Encoder)SensorController.getInstance().getSensor("ENCODER0"));
			list.addCommand(new DriveAtAngle(new DistanceStopCondition(encoders, (int)distanceToMove), slowPower,
					fastPower, degreesToTurn));
			
			
			runner=new CommandListRunner(list);
			runner.init();
			return false;
		}

	}

	public void stop() {
		runner.stop();
	}
}
