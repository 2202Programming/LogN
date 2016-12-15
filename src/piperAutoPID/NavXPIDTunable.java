package piperAutoPID;

import java.util.Random;

import com.kauailabs.navx.frc.AHRS;

import PID.AutoPIDTunable;
import PID.AutoPIDTuner;
import comms.DebugMode;
import comms.SmartWriter;
import drive.DriveControl;
import drive.IDrive;
import input.SensorController;
import robot.Global;
import robot.IControl;

public class NavXPIDTunable extends IControl implements AutoPIDTunable {

	private AHRS navx;

	private AutoPIDTuner tuner;
	
	private IDrive drive;

	private double turnPower=0;

	private boolean resetting=false;
	private double TARGET_RESET_ANGLE=40, TARGET_RESET_ANGLE_MAX_ERROR=5;

	/**
	 * Resets the navx input, so that it is facing straight forward, sets the
	 * local variables, and disables the ArcadeDrive from controlling motors
	 */
	public void autonomousInit() {
		// store the motors we need to power and the navx board as local
		// variables

		navx=(AHRS)SensorController.getInstance().getSensor("NAVX");
		navx.reset();// resets the angle

		// Disable the drive from controlling the movement
		
		drive=(IDrive)Global.controlObjects.get("ARCADE_DRIVE");;
		drive.setDriveControl(DriveControl.EXTERNAL_CONTROL);

		tuner=new AutoPIDTuner(this);
	}

	/**
	 * Call the PIDTuner's update
	 */
	public void autonomousPeriodic() {
		SmartWriter.putS("PID Tuning Status", "Starting", DebugMode.DEBUG);
		tuner.update();
		updateMotors();
	}

	public void startReset() {
		resetting=true;
		TARGET_RESET_ANGLE=40;
	}

	public void setToRandomState() {
		Random r=new Random();
		TARGET_RESET_ANGLE=r.nextDouble()*360-180;
	}

	public boolean getResetFinished() {
		if (!resetting) {
			return true;
		}
		SmartWriter.putS("PID Tuning Status", "updating", DebugMode.DEBUG);
		// If we are farther away from the target reset angle than the maxError,
		// we are still resetting
		resetting=Math.abs(getAngle()-TARGET_RESET_ANGLE)>TARGET_RESET_ANGLE_MAX_ERROR;
		if (getAngle()>TARGET_RESET_ANGLE) {
			turnPower=-0.5;
		}
		else {
			turnPower=0.5;
		}

		// The motors powered from autoPeriodic
		return false;
	}

	public double getError() {
		return getAngle();
	}

	public void setValue(double turnValue) {
		turnPower=turnValue;
	}

	private void updateMotors() {
		// Positive is right, according to NavX
		if (Math.abs(turnPower) > 1) {
			SmartWriter.putS("Warning: Turn Power Over 1", "warning", DebugMode.DEBUG);
			turnPower /= Math.abs(turnPower);
		}
		SmartWriter.putD("Turn Power", turnPower, DebugMode.DEBUG);
		drive.setLeftMotors(turnPower);
		drive.setRightMotors(-turnPower);
	}

	/**
	 * Gets the angle between -180 and 180
	 * 
	 * @return an angle from between -180 and 180 of which direction we are
	 *         currently facing
	 * 
	 */
	private double getAngle() {
		double rawAngle=navx.getAngle();
		double plus180=rawAngle+180;
		plus180=((plus180%360)+360)%360;// this will always convert plus180 from
										// between 0 and 360 in-exclusive
		return plus180-180;
	}
}
