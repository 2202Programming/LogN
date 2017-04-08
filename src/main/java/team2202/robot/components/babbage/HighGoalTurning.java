package team2202.robot.components.babbage;

import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import comms.XboxController;
import edu.wpi.first.wpilibj.Servo;
import physicalOutput.motors.ServoMotor;
import robot.Global;
import robot.IControl;
import team2202.robot.definitions.controls.BabbageControl;

public class HighGoalTurning extends IControl {

	private ServoMotor servo, heightServoMotor;
	private NetworkTables table;
	private boolean waitingToTurnShooter=false;
	private double targetAngle=90;
	//private XboxController controller;
	private boolean processingVision=false;
	
	private final double DEGREEOFFSET = -10;

	public HighGoalTurning(ServoMotor turnShooterServo, ServoMotor heightShooterMotor) {
		servo=turnShooterServo;
		table=new NetworkTables(TableNamesEnum.VisionTable);
		//controller=XboxController.getXboxController();
		heightServoMotor=heightShooterMotor;
	}

	public void teleopInit() {
		targetAngle = 90;
		servo.set(targetAngle/180);
		table.setBoolean("processVisionHighGoal", false);
		processingVision=false;
	}

	public void teleopPeriodic() {
		if (((BabbageControl)(Global.controllers)).pauseHighGoalVision()) {
			processingVision=false;
			return;
		}
		if (processingVision) {
			if (table.getBoolean("processVisionHighGoal")) {
				//still processing
			}
			else {
				processingVision=false;
				targetAngle = servo.getAngle()- table.getDouble("degreesToSetHighGoal")*2 + DEGREEOFFSET;//*3.8;
				double distance=table.getDouble("distanceFromHighGoal")+14.75/2;//radius of tape retroreflective tape (really diameter/2)
				distance -=5;//adjustment for overshooting
				SmartWriter.putS("High Goal Vision Result2:", "Distance: " +distance);
				servo.set(targetAngle/180);
				targetAngle=Math.max(0, Math.min(180, targetAngle));
				
				//these are calculated constants that we found from testing and linear regression.
				double angle=(distance*0.00504)-0.29071;
				angle=Math.max(0, Math.min(1, angle));
				heightServoMotor.set(angle);
			}
		}
		else {
			servo.set(targetAngle/180);
			if (Math.abs(servo.getAngle() - targetAngle) < 0.1) {
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
	
	public void autonomousInit() {
		teleopInit();
	}
	
	public void autonomousPeriodic() {
		teleopPeriodic();
	}
}
