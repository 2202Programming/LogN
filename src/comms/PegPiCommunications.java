package comms;

import robot.IControl;

public class PegPiCommunications extends IControl  {
	
	private VisionStates state;
	private XboxController controller;
	
	public void robotInit() {
		
	}

	public void autonomousInit() {
		controller=XboxController.getXboxController();
	}

	public void autonomousPeriodic() {
		update();
	}
	
	public void teleopInit() {
		controller=XboxController.getXboxController();
	}

	public void teleopPeriodic() {
		update();
	}
	
	private void update() {
		switch (state) {
		case WAITING_TO_TAKE_PICTURE:
			/*if (controller.get) {
				
			}*/
			break;
		case PROCESSING:			
			break;
		case TURNING:
			break;
		}
	}
	
}

enum VisionStates {
	WAITING_TO_TAKE_PICTURE, PROCESSING, TURNING
}
