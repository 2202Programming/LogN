package drive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import robot.Global;
import robotDefinitions.ControlBase;

public class MechanumDrive extends IDrive {
	private IDrive DriveMotors;
	private ControlBase controller;
	private RobotDrive cheatyDriveClass;
	private int controllerPort;
	private Talon motor;
	
	public MechanumDrive(int bl,int br, int fr, int fl,int drivePort){
		controller = Global.controllers;
		Talon temp = new Talon(fl);
		Talon temp2 = new Talon(bl);
		Talon temp3 = new Talon(fr);
		Talon temp4 = new Talon(br);
		temp.setInverted(true);
		temp2.setInverted(true);
		temp3.setInverted(false);
		temp4.setInverted(false);
		cheatyDriveClass = new RobotDrive(temp,temp2,temp3,temp4);//fl, bl, fr, br);
		controllerPort = drivePort;
		//motor = new Talon(1);
	}
	@Override
	protected void teleopUpdate() {
		
	}

	@Override
	protected void setMotors() {
		//motor.set(.2);
		cheatyDriveClass.mecanumDrive_Cartesian(-1*controller.getLeftJoystickX(controllerPort), controller.getLeftJoystickY(controllerPort), -1*controller.getRightJoystickX(controllerPort), 0);
		
	}

	@Override
	protected void disableMotors() {

	}

	@Override
	public boolean hasEncoders() {
		return false;
	}

	@Override
	public void setLeftMotors(double power) {

	}

	@Override
	public void setRightMotors(double power) {

	}

}
