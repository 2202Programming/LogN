package team2202.robot.autoCommands;

import java.util.ArrayList;

import auto.ICommand;
import auto.commands.DriveAtAngle;
import auto.stopConditions.TimerStopCondition;
import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import input.SensorController;
import robot.Global;
import team2202.robot.definitions.controls.BabbageControl;

/**
 * 
 * @author 13dha
 *
 */
public class ContinuousPegVisionCommand implements ICommand {
	private NetworkTables table;
	private double degreesToTurn;
	private double distanceToMove;
	private Ultrasonic distanceSensor;//7 is not in use
	private DriveAtAngle driveAtAngleCommand;
	private ArrayList<Encoder> encoders;
	private double lastAngle=0;
	private long msToRunFor=99999999;
	private long maxEndTime=Long.MAX_VALUE;

	
	/**
	 * Constructs a ContinuousPegVision command object
	 * 
	 * @param percentToFinish
	 * We don't actually use this at all anymore. We used to use it 
	 * based on our distance results from when we were measuring 
	 * distance from vision. We use an ultrasonic sensor now, though
	 */
	public ContinuousPegVisionCommand(double percentToFinish) {
		table=new NetworkTables(TableNamesEnum.VisionTable);
		driveAtAngleCommand=new DriveAtAngle(new TimerStopCondition(10000), 0.3, 0);
		
		distanceSensor=(Ultrasonic) SensorController.getInstance().getSensor("DISTANCESENSOR");
		distanceSensor.setAutomaticMode(true);
		
	}
	
	/**
	 * Constructs a ContinuousPegVision command that stops after a certain number of milliseconds
	 * @param percentToFinish
	 * We don't actually use this at all anymore. We used to use it 
	 * based on our distance results from when we were measuring 
	 * distance from vision. We use an ultrasonic sensor now, though
	 * @param msToRunFor
	 * The number of milliseconds before this command ends. We use this in autonomous in case we get stuck on the wall.
	 */
	public ContinuousPegVisionCommand(double percentToFinish, long msToRunFor) {
		this(percentToFinish);
		this.msToRunFor=msToRunFor;
	}

	//comments in ICommand
	public void init() {
		SmartWriter.putD("Peg Vision activated", System.currentTimeMillis());
		driveAtAngleCommand.init();
		driveAtAngleCommand.setAngle(0);//drive forward until we know what angle we should drive at
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
		
		
		//Go slower if we are closer to where we want to be. If we are less than 10 inches from the target, the distance sensor will no longer work, so stop.
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
		
		driveAtAngleCommand.run();//run the command to drive at an angle
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

			//double distanceTraveled=encoders.get(0).getDistance();
			//double distanceX=Math.cos(Math.toRadians(degreesToTurn))*distanceTraveled;
			//double distanceY=Math.sin(Math.toRadians(degreesToTurn))*distanceTraveled;
			//double increasedAngle=Math.toDegrees(Math.atan2(distanceY, distanceToMove-distanceX));
			//degreesToTurn+=increasedAngle;
			//distanceToMove=Math.hypot(distanceToMove-distanceX, distanceY);

			double tempLastAngle=lastAngle;
			lastAngle=driveAtAngleCommand.getAngle();
			driveAtAngleCommand.setAngle(tempLastAngle+degreesToTurn);//turn at the angle vision was when we took the picture, plus what vision said to turn
			
			table.setBoolean("processVision", true);
			encoders.get(0).reset();//we don't use this anymore, it was from when we used encoders and vision to measure distance
			return false;
		}

	}

	public void stop() {
		driveAtAngleCommand.stop();
	}
}
