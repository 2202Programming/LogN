package tim;

import comms.DebugMode;
import comms.SmartWriter;
import comms.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import physicalOutput.IMotor;
import physicalOutput.SolenoidController;
import robot.IControl;

public class Shooter extends IControl {
	// 0-disabled 1-enabled 2-external
	public int state;
	private IMotor left;
	private IMotor right;
	private IMotor height;
	private XboxController controller;
	private double curSpeed;
	private SolenoidController sc;
	private DoubleSolenoid trigger;

	public Shooter(IMotor left, IMotor right, IMotor height) {
		state = 1;
		this.left = left;
		this.right = right;
		this.height = height;
		controller = XboxController.getXboxController();
		sc=SolenoidController.getInstance();
		curSpeed = 0;
		try {
			sc.getDoubleSolenoid("TRIGGER");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			SmartWriter.putS("SolNcrushed", "Something goes wrong", DebugMode.DEBUG);
		}
	}

	public void autonomousInit() {
		curSpeed = 0;
		setShootRaw(0);
		setHeightRaw(0);
	}

	public void teleopInit() {
		curSpeed = 0;
		setShootRaw(0);
		setHeightRaw(0);
	}

	public void teleopPeriodic() {
		//sets the speed for the shooter wheels Right bumper - faster, Left bumper - slower
		if (controller.getBPressed()) {
			curSpeed = 0;
		}
		else if (controller.getRightBumperPressed() && curSpeed < 1) {
			curSpeed += .2;
		}
		else if (controller.getLeftBumperPressed() && curSpeed > 0) {
			curSpeed -= .2;
		}
		setShootRaw(curSpeed);

		//Sets the elevation of the shooter Y - up, X - down
		if (controller.getXHeld()) {
			setHeightRaw(-1.0);
		}
		else if (controller.getYHeld()) {
			setHeightRaw(0.75);
		}
		
		if(controller.getAHeld()) 
		{
			SmartWriter.putB("ABotten", true, DebugMode.DEBUG);
			trigger.set(Value.kForward);
		}
		else{
			SmartWriter.putB("ABotten", false, DebugMode.DEBUG);
			trigger.set(Value.kReverse);
		}
		SmartWriter.putS("solState", trigger.get() + " ", DebugMode.DEBUG);
	
	}

	private void setShootRaw(double power) {
		left.setSpeed(power);
		right.setSpeed(power);
	}

	private void setHeightRaw(double speed) {
		height.setSpeed(speed);
	}

	/**
	 * sets both of the shoot motors to power <br>
	 * Preconditions: power is between 1.0 and -1.0 <br>
	 * Postconditions: sets the power
	 * 
	 * @param power
	 */
	public void setShoot(double power) {
		if (state == 2) {
			setShootRaw(power);
		}
	}

	/**
	 * Sets the motor for elevation change positive goes up negative goes down
	 * Preconditions: speed is between 1.0 and -1.0 Postconditions: sets the
	 * motor
	 * 
	 * @param speed
	 */
	public void setHeight(double speed) {
		if (state == 2) {
			setHeightRaw(speed);
		}
	}
}
