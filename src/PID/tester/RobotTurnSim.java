package PID.tester;

import java.util.Random;

import PID.AutoPIDTunable;

public class RobotTurnSim implements AutoPIDTunable{

	private double angle=0, turnValue=0, targetAngle=0;
	//private final double minTurnAmount=0.0;
	private boolean resetting=true;
	private double angleToTurnTo=2;
	private double turnVelocity=0.0, turnFriction=0.98, turnPower=0.0001;
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
		System.out.println("Turning to "+angleToTurnTo);
	}

	public boolean getResetFinished() {
		return !resetting;
	}

	public double getError() {
		return angle-targetAngle;
	}

	public void setValue(double turnValue) {
		this.turnValue=turnValue;
	}
	
	public void update() {
		if (resetting) {
			if (Math.abs(angle-angleToTurnTo)>0.02) {
				angle+=Math.signum(angleToTurnTo-angle)*0.01;
			}
			else {
				resetting=false;
			}
		}
		else {
			turnVelocity+=turnPower*turnValue;
			angle+=turnVelocity;
			turnVelocity*=turnFriction;
		}
	}
	
	public double getAngle() {
		return angle;
	}

}
