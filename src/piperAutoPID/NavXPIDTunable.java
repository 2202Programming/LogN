package piperAutoPID;

import com.kauailabs.navx.frc.AHRS;

import PID.AutoPIDTunable;
import drive.ArcadeDrive;
import drive.DriveControl;
import input.SensorController;
import physicalOutput.IMotor;
import robot.Global;
import robot.IControl;

public class NavXPIDTunable extends IControl implements AutoPIDTunable {
	public void autonomousInit() {
		IControl drive=Global.controlObjects.get("ARCADE_DRIVE");
		ArcadeDrive arcadeDrive=(ArcadeDrive)drive;
		arcadeDrive.setDriveControl(DriveControl.EXTERNAL_CONTROL);
	}

	@Override
	public void startReset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setToRandomState() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getResetFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getError() {
		AHRS navx = (AHRS)SensorController.getInstance().getSensor("NAVX");
		double navxAngle = navx.getAngle();
		return navxAngle;
	}

	@Override
	public void setValue(double turnValue) {
		IMotor FL =(IMotor) Global.controlObjects.get("FRONT_LEFT_MOTOR");
		IMotor FR =(IMotor) Global.controlObjects.get("FRONT_RIGHT_MOTOR");
		IMotor BL =(IMotor) Global.controlObjects.get("BACK_LEFT_MOTOR");
		IMotor BR =(IMotor) Global.controlObjects.get("BACK_RIGHT_MOTOR");
		
		//Positive is right
		FL.setSpeed(turnValue);
		BL.setSpeed(turnValue);
		FR.setSpeed(-turnValue);
		BR.setSpeed(-turnValue);
		
	}

}
