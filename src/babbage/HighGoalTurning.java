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
	private BabbageControl babbageControl;
	
	public HighGoalTurning() {
		servo=new Servo(8);
		table=new NetworkTables(TableNamesEnum.VisionTable);
		babbageControl=(BabbageControl)Global.controllers;
	}
	
	
	public void teleopInit() {
		table.setBoolean("processVisionHighGoal", true);
	}
	
	public void teleopPeriodic() {
		
		if (!waitingToTurnShooter) {
			if (!table.getBoolean("processVisionHighGoal")) {
				targetAngle=servo.getAngle()+table.getDouble("degreesToSetHighGoal");
				servo.setAngle(targetAngle);
				waitingToTurnShooter=true;
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
