package PID.tester;

import java.util.Random;

import PID.AutoPIDTunable;
import PID.PIDValues;

public class RobotTurnSim implements AutoPIDTunable{

	private double angle=0, turnValue=0;
	//private final double minTurnAmount=0.0;
	private boolean resetting=true;
	private double angleToTurnTo=2;
	private double turnVelocity=0.0, turnFriction=0.85, turnPower=0.01;
	private Random random=new Random();
	
	public void startReset() {
		resetting=true;
		angleToTurnTo=1;
	}

	public void setToRandomState() {
		resetting=true;
		angleToTurnTo=random.nextDouble()*2;
		if (random.nextBoolean()) {
			angleToTurnTo*=-1;
		}
	}

	public boolean getResetFinished() {
		return !resetting;
	}

	public double getError() {
		return angle;
	}

	public void setValue(double turnValue) {
		if (Math.abs(turnValue)>1) {
			turnValue=1*Math.signum(turnValue);
		}
		this.turnValue=turnValue;
	}
	
	public void update() {
		if (resetting) {
			if (angle>5||angle<-5) {
				angle=0;
			}
			if (Math.abs(angle-angleToTurnTo)>0.02) {
				angle+=Math.signum(angleToTurnTo-angle)*0.01;
			}
			else {
				resetting=false;
			}
		}
		else {
			double minValue=0.4, usedValue=0;
			if (Math.abs(turnValue)<minValue) {
				usedValue=0;
			}
			else {
				usedValue=(Math.abs(turnValue)-minValue)*Math.signum(turnValue);
			}
			turnVelocity+=usedValue*turnPower;
			angle+=turnVelocity;
			turnVelocity*=turnFriction;
		}
	}
	
	public double getAngle() {
		return angle;
	}

	//TODO fix this
	public void giveInfo(PIDValues bestValues, int bestTuneTime, PIDValues testingValues, int lastTestTime) {
		// TODO Auto-generated method stub
		
	}

	

}
