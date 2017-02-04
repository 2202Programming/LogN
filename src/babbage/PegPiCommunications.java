package babbage;

import comms.DebugMode;
import comms.NetworkTables;
import comms.SmartWriter;
import comms.TableNamesEnum;
import comms.XboxController;
import robot.IControl;

public class PegPiCommunications extends IControl  {
	
	private VisionStates state=VisionStates.WAITING_TO_TAKE_PICTURE;
	private XboxController controller;
	private NetworkTables table;
	
	public PegPiCommunications() {		
		table=new NetworkTables(TableNamesEnum.VisionTable);
	}
	
	public void robotInit() {
	}
	
	public void autonomousInit() {
		init();
	}

	public void autonomousPeriodic() {
		update();
	}
	
	public void teleopInit() {
		init();
	}

	public void teleopPeriodic() {
		update();
	}

	private void init() {
		controller=XboxController.getXboxController();
		state=VisionStates.WAITING_TO_TAKE_PICTURE;
	}
	
	private void update() {
		SmartWriter.putS("Peg Vision State", state.toString(), DebugMode.DEBUG);
		switch (state) {
		case WAITING_TO_TAKE_PICTURE:
			if (controller.getXPressed()) {
				table.setBoolean("processVision", true);
				state=VisionStates.PROCESSING;
			}
			break;
		case PROCESSING:
			if (table.getBoolean("processVision")) {
				//wait for the processing to finish
			}
			else {
				state=VisionStates.TURNING;
				SmartWriter.putD("Degrees to turn from vision", table.getDouble("degreesToTurn"), DebugMode.DEBUG);
			}
			break;
		case TURNING:
			//TODO actually turn
			state=VisionStates.WAITING_TO_TAKE_PICTURE;
			break;
		}
	}
	
}

enum VisionStates {
	WAITING_TO_TAKE_PICTURE, PROCESSING, TURNING
}