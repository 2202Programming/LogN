package babbage;

import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import comms.XboxController;
import edu.wpi.first.wpilibj.Servo;
import robot.IControl;

public class HighGoalTurning extends IControl {

	private Servo servo;
	private NetworkTables table;
	private boolean waitingToTurnShooter=false;
	private double targetAngle=0;
	private XboxController controller;
	private boolean processingVision=false;

	public HighGoalTurning() {
		servo=new Servo(8);
		table=new NetworkTables(TableNamesEnum.VisionTable);
		controller=XboxController.getXboxController();
	}

	public void teleopInit() {
		table.setBoolean("processVisionHighGoal", false);
		processingVision=false;
	}

	public void teleopPeriodic() {
		if (processingVision) {
			if (table.getBoolean("processVisionHighGoal")) {
				//still processing
			}
			else {
				processingVision=false;
				double angle=table.getDouble("degreesToSetHighGoal");
				SmartWriter.putS("High Goal Vision Result:", "Distance: "+table.getDouble("distanceFromHighGoal")+
						"   Angle: "+angle);
			}
		}
		else {
			if (controller.getYPressed()) {
				table.setBoolean("processVisionHighGoal", true);
				processingVision=true;
			}
		}
		/*
		 * if (!waitingToTurnShooter) { if
		 * (!table.getBoolean("processVisionHighGoal")) {
		 * targetAngle=servo.getAngle()-table.getDouble("degreesToSetHighGoal");
		 * servo.setAngle(servo.getAngle()+table.getDouble(
		 * "degreesToSetHighGoal")); SmartWriter.putD("High Goal angle",
		 * table.getDouble("degreesToSetHighGoal")); targetAngle=0;
		 * waitingToTurnShooter=true;
		 * 
		 * 
		 * } } else { double error=Math.abs(servo.getAngle()-targetAngle);
		 * SmartWriter.putD("High Goal Error", error); if (error<0.1) {
		 * waitingToTurnShooter=false; table.setBoolean("processVisionHighGoal",
		 * true); } }
		 */
	}
}
