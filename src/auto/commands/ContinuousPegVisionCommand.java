package auto.commands;

import java.util.ArrayList;

import auto.CommandList;
import auto.CommandListRunner;
import auto.ICommand;
import auto.stopConditions.DistanceStopCondition;
import auto.stopConditions.TimerStopCondition;
import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import drive.DriveControl;
import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import input.SensorController;
import robot.Global;
import robotDefinitions.BabbageControl;

public class ContinuousPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private boolean doneWithVision=false;
	private double distance;
	private Ultrasonic distanceSensor;//7 is not in use
	private DriveAtAngle driveAtAngleCommand;
	private ArrayList<Encoder> encoders;
	private double lastAngle=0;
	private long msToRunFor=99999999;
	private long maxEndTime=Long.MAX_VALUE;

	public ContinuousPegVisionCommand(double percentToFinish) {
		table=new NetworkTables(TableNamesEnum.VisionTable);
		driveAtAngleCommand=new DriveAtAngle(new TimerStopCondition(10000), 0.3, 0);
		
		distanceSensor=(Ultrasonic) SensorController.getInstance().getSensor("DISTANCESENSOR");
		distanceSensor.setAutomaticMode(true);
		
	}
	
	public ContinuousPegVisionCommand(double percentToFinish, long msToRunFor) {
		this(percentToFinish);
		this.msToRunFor=msToRunFor;
	}

	public void init() {
		SmartWriter.putD("Peg Vision activated", System.currentTimeMillis());
		doneWithVision=false;
		driveAtAngleCommand.init();
		driveAtAngleCommand.setAngle(0);
		table.setBoolean("processVision", true);
		encoders=new ArrayList<>();
		encoders.add((Encoder)SensorController.getInstance().getSensor("ENCODER0"));
		encoders.get(0).reset();
		lastAngle=0;
		maxEndTime=System.currentTimeMillis()+msToRunFor;
	}

	public boolean run() {
		if (((BabbageControl)(Global.controllers)).cancelPegVision()) {
			stop();
			table.setBoolean("processVision", false);
			return true;
		}
		
		if (System.currentTimeMillis()>=maxEndTime) {
			stop();
			return true;
		}
		
		
		if (distanceSensor.getRangeInches()<24) {
			driveAtAngleCommand.setSpeed(0.2);
			if (distanceSensor.getRangeInches()<10) {
				stop();
				return true;
			}
		}
		else {
			if (distanceSensor.getRangeInches()<40) {				
				driveAtAngleCommand.setSpeed(0.3);
			}
			else {
				if (distanceSensor.getRangeInches()<60) {					
					driveAtAngleCommand.setSpeed(0.4);
				}
				else {
					driveAtAngleCommand.setSpeed(0.5);
				}
			}
		}
		
		driveAtAngleCommand.run();
		SmartWriter.putD("Distance Sensor", distanceSensor.getRangeInches());
		
		if (table.getBoolean("processVision")) {
			//waitingForVision
			SmartWriter.putS("Peg Vision State", "Visioning");
			return false;
		}
		else {
			// vision is done
			degreesToTurn=table.getDouble("degreesToTurn");
			distanceToMove=table.getDouble("distanceToMove");
			SmartWriter.putD("degreesToTurn final", degreesToTurn);
			SmartWriter.putD("distanceToMove final", distanceToMove);

			double distanceTraveled=encoders.get(0).getDistance();
			double distanceX=Math.cos(Math.toRadians(degreesToTurn))*distanceTraveled;
			double distanceY=Math.sin(Math.toRadians(degreesToTurn))*distanceTraveled;
			double increasedAngle=Math.toDegrees(Math.atan2(distanceY, distanceToMove-distanceX));
			//degreesToTurn+=increasedAngle;
			distanceToMove=Math.hypot(distanceToMove-distanceX, distanceY);

			double tempLastAngle=lastAngle;
			lastAngle=driveAtAngleCommand.getAngle();
			driveAtAngleCommand.setAngle(tempLastAngle+degreesToTurn);
			
			table.setBoolean("processVision", true);
			encoders.get(0).reset();
			return false;
		}

	}

	public void stop() {
		driveAtAngleCommand.stop();
	}
}
