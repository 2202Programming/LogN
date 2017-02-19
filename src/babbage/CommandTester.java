package babbage;

import auto.CommandList;
import auto.CommandListRunner;
import comms.DebugMode;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class CommandTester extends IControl {

	private CommandList commandList;
	private BabbageControl controllers = (BabbageControl)Global.controllers;
	private DriverStation ds=DriverStation.getInstance();
	private String autoName;

	public CommandTester() {
		/*
		 * commandList = BabbageAutoLists.centerBlue(); new
		 * CommandListRunner(commandList);
		 */

		boolean blueSide = false;
		if (ds.getAlliance() == Alliance.Red) {
			blueSide = false;
		}
		else {
			blueSide = true;
		}

		if ( !blueSide) {
			if (controllers.autoFieldPosition1()) {
				commandList = BabbageAutoLists.centerRed();
				autoName = "centerRed";
			}
			if (controllers.autoFieldPosition2()) {
				commandList = BabbageAutoLists.boilerRed();
				autoName = "boilerRed";
			}
			if (controllers.autoFieldPosition0()) {
				commandList = BabbageAutoLists.notBoilerRed();
				autoName = "notBoilerRed";
			}
		}

		if (blueSide) {
			if (controllers.autoFieldPosition1()) {
				commandList = BabbageAutoLists.centerBlue();
				autoName = "centerBlue";
			}
			if (controllers.autoFieldPosition0()) {
				commandList = BabbageAutoLists.boilerBlue();
				autoName = "boilerBlue";
			}
			if (controllers.autoFieldPosition2()) {
				commandList = BabbageAutoLists.notBoilerBlue();
				autoName = "notBoilerBlue";
			}
		}
		SmartWriter.putS("AutoMode", autoName, DebugMode.COMPETITION);
		new CommandListRunner(commandList);
	}

	public void autonomousInit() {

	}

}
