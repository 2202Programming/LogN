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
	private CommandListRunner runner;

	public CommandTester() {
		/*
		 * commandList = BabbageAutoLists.centerBlue(); new
		 * CommandListRunner(commandList);
		 */

	}

	public void autonomousInit() {
		runner = new CommandListRunner(BabbageAutoLists.centerBlue());
		runner.init();
//		boolean blueSide = false;
//		if (ds.getAlliance() == Alliance.Red) {
//			blueSide = false;
//		}
//		else {
//			blueSide = true;
//		}
//		
//		SmartWriter.putB("pos0", controllers.autoFieldPosition0(), DebugMode.COMPETITION);
//		SmartWriter.putB("pos1", controllers.autoFieldPosition1(), DebugMode.COMPETITION);
//		SmartWriter.putB("pos2", controllers.autoFieldPosition2(), DebugMode.COMPETITION);
//		autoName = "none";
//		if ( !blueSide) {
//			if (controllers.autoFieldPosition1()) {
//				commandList = BabbageAutoLists.centerRed();
//				autoName = "centerRed";
//			}
//			if (controllers.autoFieldPosition2()) {
//				commandList = BabbageAutoLists.boilerRed();
//				autoName = "boilerRed";
//			}
//			if (controllers.autoFieldPosition0()) {
//				commandList = BabbageAutoLists.notBoilerRed();
//				autoName = "notBoilerRed";
//			}
//		}
//		
//		if (blueSide) {
//			if (controllers.autoFieldPosition1()) {
//				commandList = BabbageAutoLists.centerBlue();
//				autoName = "centerBlue";
//			}
//			if (controllers.autoFieldPosition0()) {
//				commandList = BabbageAutoLists.boilerBlue();
//				autoName = "boilerBlue";
//			}
//			if (controllers.autoFieldPosition2()) {
//				commandList = BabbageAutoLists.notBoilerBlue();
//				autoName = "notBoilerBlue";
//			}
//		}
		SmartWriter.putS("AutoMode", "hello", DebugMode.COMPETITION);
	}
	
	public void autonomousPeriodic(){
//		if(autoName.equals("none")){
//			autonomousInit();
//		}else{
//			if(runner == null){
//				runner = new CommandListRunner(commandList);
//			}
//		}
		
		runner.runList();
	}
	
	public void teleopInit(){
		runner.stop();
	}
	
}
