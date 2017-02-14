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
	
	public HighGoalTurning() {
		servo=new Servo(8);
		table=new NetworkTables(TableNamesEnum.VisionTable);

	}
	
	
	public void teleopInit() {
		table.setBoolean("processVisionHighGoal", true);
		SmartWriter.putD("NumberToTurnTo5", 50);
		SmartWriter.putD("High Goal angle", table.getDouble("degreesToSetHighGoal"));
		SmartWriter.putD("now angle", servo.getAngle());
	}
	
	public void teleopPeriodic() {
		servo.setAngle(SmartWriter.getD("NumberToTurnTo5"));
		/*if (!waitingToTurnShooter) {
			if (!table.getBoolean("processVisionHighGoal")) {
				targetAngle=servo.getAngle()-table.getDouble("degreesToSetHighGoal");
				servo.setAngle(servo.getAngle()+table.getDouble("degreesToSetHighGoal"));
				SmartWriter.putD("High Goal angle", table.getDouble("degreesToSetHighGoal"));
				targetAngle=0;
				waitingToTurnShooter=true;

				
			}
		}
		else {
			double error=Math.abs(servo.getAngle()-targetAngle);
			SmartWriter.putD("High Goal Error", error);
			if (error<0.1) {
				waitingToTurnShooter=false;
				table.setBoolean("processVisionHighGoal", true);
			}
		}*/
	}
}
