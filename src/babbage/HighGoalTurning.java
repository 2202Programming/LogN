package babbage;

import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import edu.wpi.first.wpilibj.Servo;
import robot.IControl;

public class HighGoalTurning extends IControl {

	private Servo servo;
	private NetworkTables table;
	private boolean waitingToTurnShooter=false;
	private double targetAngle=0;
	private int lookingForHighGoalState=0;
	
	public HighGoalTurning() {
		servo=new Servo(8);
		table=new NetworkTables(TableNamesEnum.VisionTable);

	}
	
	
	public void teleopInit() {
		table.setBoolean("processVisionHighGoal", true);
	}
	
	public void teleopPeriodic() {
		if (!waitingToTurnShooter) {
			if (!table.getBoolean("processVisionHighGoal")) {
				//Vision is done
				if (table.getBoolean("HighGoalVisionSucceeded")) {					
					targetAngle=servo.getAngle()+table.getDouble("degreesToSetHighGoal");
					servo.setAngle(targetAngle);
					waitingToTurnShooter=true;
				}
				else {
					//if we don't find it, we will follow the pattern: 0, 30, 60, 90, 120, 150, 180, 0, 30...
					lookingForHighGoalState++;
					lookingForHighGoalState%=7;
					targetAngle=lookingForHighGoalState*30;
					servo.setAngle(targetAngle);
					waitingToTurnShooter=true;
				}
			}
		}
		else {
			double error=Math.abs(servo.getAngle()-targetAngle);
			SmartWriter.putD("High Goal Error", error);
			if (error<0.1) {
				waitingToTurnShooter=false;
				table.setBoolean("processVisionHighGoal", true);
			}
		}
	}
}
