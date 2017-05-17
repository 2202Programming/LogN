package team2202.robot.definitions.controls;

import comms.XboxController;
import robotDefinitions.ControlBase;

public class HoenhiemControl extends ControlBase {

	@Override
	public XboxController[] getControllers() {
		int a=5;
		return new XboxController[] {XboxController.getXboxController()};
	}

}
