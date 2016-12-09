package piperAutoPID;

import java.util.Random;

import com.kauailabs.navx.frc.AHRS;

import PID.AutoPIDTunable;
import PID.AutoPIDTuner;
import drive.ArcadeDrive;
import drive.DriveControl;
import input.SensorController;
import physicalOutput.IMotor;
import robot.Global;
import robot.IControl;

public class NavXPIDTunable extends IControl implements AutoPIDTunable {

	private AHRS navx;

	private AutoPIDTuner tuner;

	private IMotor FL, FR, BL, BR;
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
		FL=(IMotor)Global.controlObjects.get("FRONT_LEFT_MOTOR");
		FR=(IMotor)Global.controlObjects.get("FRONT_RIGHT_MOTOR");
		BL=(IMotor)Global.controlObjects.get("BACK_LEFT_MOTOR");
		BR=(IMotor)Global.controlObjects.get("BACK_RIGHT_MOTOR");

		navx=(AHRS)SensorController.getInstance().getSensor("NAVX");
		navx.reset();// resets the angle

		// Disable the drive from controlling the movement
		IControl drive=Global.controlObjects.get("ARCADE_DRIVE");
		ArcadeDrive arcadeDrive=(ArcadeDrive)drive;
		arcadeDrive.setDriveControl(DriveControl.EXTERNAL_CONTROL);

		tuner=new AutoPIDTuner(this);
	}

	/**
	 * Call the PIDTuner's update
	 */
	public void autonomousPeriodic() {
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
		return !resetting;
	}

	public double getError() {
		return getAngle();
	}

	public void setValue(double turnValue) {
		turnPower=turnValue;
	}

	private void updateMotors() {
		// Positive is right, according to NavX
		FL.setSpeed(turnPower);
		BL.setSpeed(turnPower);
		FR.setSpeed(-turnPower);
		BR.setSpeed(-turnPower);
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
