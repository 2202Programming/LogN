package auto.commands;

import java.util.ArrayList;

import auto.ICommand;
import auto.stopConditions.DistanceStopCondition;
import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import drive.ArcadeDrive;
import drive.DriveControl;
import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import input.SensorController;
import robot.Global;

public class DrivingPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private boolean doneWithVision = false;
	private ArrayList<ICommand> subcommands = new ArrayList<>();
	private double percentToFinish = 0;
	private ArrayList<Encoder> encoders;
	private IDrive drive;

	public DrivingPegVisionCommand(double percentToFinish) {
		table = new NetworkTables(TableNamesEnum.VisionTable);
		this.percentToFinish = percentToFinish;
	}

	public void init() {
		SmartWriter.putD("Peg Vision activated", System.currentTimeMillis());
		doneWithVision = false;
		subcommands.clear();
		table.setBoolean("processVision", true);
		drive = (IDrive) Global.controlObjects.get("DRIVE");
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
		drive.setLeftMotors(0.5);
		drive.setRightMotors(0.5);

		encoders = new ArrayList<>();
		encoders.add((Encoder) SensorController.getInstance().getSensor("ENCODER0"));
		encoders.get(0).reset();
	}

	public boolean run() {
		if (doneWithVision) {
		
			if (subcommands.isEmpty()) {
				SmartWriter.putS("Peg Vision State", "Done, but I shouldn't be here");
				return true;
			} else {
				SmartWriter.putS("Peg Vision State", "Turning/Driveing");
				if (subcommands.get(0).run()) {
					subcommands.remove(0);
					if (!subcommands.isEmpty()) {
						subcommands.get(0).init();
					} else {
						SmartWriter.putS("Peg Vision State", "Done");
						//((ArcadeDrive)drive).setReverse(false);
						return true;
					}
				}
				return false;
			}
		}
		if (table.getBoolean("processVision")) {
			SmartWriter.putS("Peg Vision State", "Visioning");
			// vision is still running
			return false;
		} else {
			// vision is done
			//((ArcadeDrive)drive).setReverse(true);
			degreesToTurn = table.getDouble("degreesToTurn");
			distanceToMove = table.getDouble("distanceToMove");
			SmartWriter.putD("degreesToTurn final", degreesToTurn);
			SmartWriter.putD("distanceToMove final", distanceToMove);
			doneWithVision = true;

			subcommands.add(new TurnCommand(degreesToTurn, 0.5, .001));
			subcommands.get(0).init();

			double distanceTraveled = encoders.get(0).getDistance();
			double distanceX = Math.cos(Math.toRadians(degreesToTurn)) * distanceTraveled;
			double distanceY = Math.sin(Math.toRadians(degreesToTurn)) * distanceTraveled;
			double increasedAngle = Math.toDegrees(Math.atan2(distanceY, distanceToMove - distanceX));
			degreesToTurn += increasedAngle;
			distanceToMove = Math.hypot(distanceToMove - distanceX, distanceY);

			distanceToMove *= percentToFinish;

			// Sets the power based on rangeFinder
			float slowPower = .3f;
			float fastPower = .6f;
			//Ultrasonic rangeFinder = (Ultrasonic) SensorController.getInstance().getSensor("RANGE");
			//if (rangeFinder.getRangeInches() < 12) {
			//	slowPower = .2f;
			//	fastPower = .4f;
			//}

			ArrayList<Encoder> encoders = new ArrayList<>();
			encoders.add((Encoder) SensorController.getInstance().getSensor("ENCODER0"));
			subcommands.add(new DriveAtAngle(new DistanceStopCondition(encoders, (int) distanceToMove), slowPower,
					fastPower, degreesToTurn));
			return false;
		}
	}
}
