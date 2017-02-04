package auto.stopConditions;

import com.kauailabs.navx.frc.AHRS;

import auto.IStopCondition;
import comms.SmartWriter;
import input.SensorController;
import robot.Global;

public class AngleStopCondition implements IStopCondition{
	
	private double degreesToTurn;
	private double marginOfError;
	private double secondsInRange;
	private long firstTimeInRightSpot;
	private AHRS navX;
	
	
	/**
	 * A stop condition for turning a given number of degrees
	 * 
	 * @param degreesToTurn
	 * The number of degrees to turn from where I am facing, with positive being right
	 * @param marginOfError
	 * The range of error from center that is still considered being facing the right angle
	 * @param secondsInRange
	 * The number of seconds that we have to be facing the right angle in a row to count as facing forward
	 */
	public AngleStopCondition(double degreesToTurn, double marginOfError, double secondsInRange) {
		this.degreesToTurn=degreesToTurn;
		this.marginOfError=marginOfError;
		this.secondsInRange=secondsInRange;
		navX=(AHRS)(SensorController.getInstance().getSensor("NAVX"));
	}


	public void init() {
		navX.reset();
	}


	public boolean stopNow() {
		double angle=navX.getAngle();
		if (Math.abs(angle-degreesToTurn)>marginOfError) {
			//not in the area
			firstTimeInRightSpot=System.currentTimeMillis()+20;//soonest possible is next update
		}
		else {
			if (System.currentTimeMillis()-secondsInRange*1000>firstTimeInRightSpot) {
				return true;
			}
		}
		return false;
	}
	
	//negative if we need to turn right
	public double getError() {
		SmartWriter.putS("NAVXFirmware", navX.getFirmwareVersion());
		double angle=navX.getAngle();
		if (angle>180) {
			angle=angle-360;
		}
		return angle-degreesToTurn;
	}
	
	
	
}
