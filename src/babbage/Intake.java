package babbage;

import comms.XboxController;
import physicalOutput.IMotor;
import physicalOutput.SparkMotor;
import robot.IControl;

public class Intake extends IControl {
	private IMotor[] intakeMotors;
	private XboxController controller;
	private double speed=0;
	
	//775 motors
	//2 motors ports 4 and 5
	public Intake() {
		intakeMotors=new SparkMotor[2];
		intakeMotors[0]=new SparkMotor(4, true);
		intakeMotors[1]=new SparkMotor(5, true);
	}
	
	public void robotInit() {
		speed=0;
	}

	public void autonomousInit() {
		speed=0;
		controller=XboxController.getXboxController();
	}

	public void autonomousPeriodic() {
		update();
	}
	
	public void teleopInit() {
		speed=0;
		controller=XboxController.getXboxController();
	}

	public void teleopPeriodic() {
		update();
	}
	
	private void update() {
		if (controller.getAPressed()) {
			speed+=0.25;
			speed%=1.25;
		}
		//for (IMotor motor:intakeMotors) {
			intakeMotors[1].setSpeed(speed);
			intakeMotors[0].setSpeed(speed);
		//}
	}
}
