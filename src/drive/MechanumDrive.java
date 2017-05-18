package drive;

import comms.XboxController;
import edu.wpi.first.wpilibj.RobotDrive;
import physicalOutput.motors.IMotor;

public class MechanumDrive extends IMotor {
	private RobotDrive drive;
	private XboxController controller;
	
	public MechanumDrive(int fl, int bl, int fr, int br){
		drive = new RobotDrive(fl, bl, fr, br);	
		controller = XboxController.getXboxController();
	}
	@Override
	protected void setMotor(double x) {
		drive.mecanumDrive_Cartesian(-controller.getLeftJoystickY(true), controller.getRightJoystickX(true), controller.getLeftJoystickY(true), 0);
	}

}
