package babbage;

import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import edu.wpi.first.wpilibj.Servo;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class HighGoalTurning extends IControl {

	private Servo servo;
	private NetworkTables table;
	private boolean waitingToTurnShooter=false;
	private double targetAngle=0;
	private int lookingForHighGoalState=0;
	private BabbageControl babbageControl;
	
	public HighGoalTurning() {
		servo=new Servo(8);
		table=new NetworkTables(TableNamesEnum.VisionTable);
		babbageControl=(BabbageControl)Global.controllers;
	}
	
	
	public void teleopInit() {
		table.setBoolean("processVisionHighGoal", true);
		waitingToTurnShooter=true;
		targetAngle=90;
		servo.setAngle(targetAngle);
	}
	
	public void teleopPeriodic() {
		
		if (!waitingToTurnShooter) {
			if (!table.getBoolean("processVisionHighGoal")) {
				SmartWriter.putB("ProcessVisionHighGoal", table.getBoolean("processVisionHighGoal"));
				//Vision is done
				SmartWriter.putD("VisionSucessLevel", table.getDouble("HighGoalVisionSucceeded"));
				if (table.getBoolean("HighGoalVisionSucceeded")) {					
					targetAngle=servo.getAngle()+table.getDouble("degreesToSetHighGoal");
					targetAngle=Math.max(0, Math.min(targetAngle, 180));
					servo.setAngle(targetAngle);
					waitingToTurnShooter=true;
				}
				else {
					//if we don't find it, we will follow the pattern: 0, 30, 60, 90, 120, 150, 180, 0, 30...
					lookingForHighGoalState++;
					lookingForHighGoalState%=18;
					targetAngle=lookingForHighGoalState*10;
					servo.setAngle(targetAngle);
					waitingToTurnShooter=true;
				}
			}
		}
		else {
			double error=Math.abs(servo.getAngle()-targetAngle);
			SmartWriter.putD("High Goal Error", error);
			if (error<0.1&&!babbageControl.pauseHighGoalVision()) {
				waitingToTurnShooter=false;
				table.setBoolean("processVisionHighGoal", true);
			}
		}
	}
}
