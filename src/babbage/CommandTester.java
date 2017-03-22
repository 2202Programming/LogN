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
		runner = null;
		boolean blueSide = false;
		if (ds.getAlliance() == Alliance.Red) {
			blueSide = false;
		}
		else {
			blueSide = true;
		}
		
		autoName = "none";
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
			
			if (controllers.autoShooter()) {
				commandList = BabbageAutoLists.shootingRed();
				autoName="shootingRed";
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
			if (controllers.autoShooter()) {
				commandList = BabbageAutoLists.shootingBlue();
				autoName="shootingBlue";
			}
		}
	}
	
	public void autonomousPeriodic(){
		SmartWriter.putB("pos0", controllers.autoFieldPosition0(), DebugMode.COMPETITION);
		SmartWriter.putB("pos1", controllers.autoFieldPosition1(), DebugMode.COMPETITION);
		SmartWriter.putB("pos2", controllers.autoFieldPosition2(), DebugMode.COMPETITION);
		SmartWriter.putS("AutoName", autoName, DebugMode.COMPETITION);
		if(autoName.equals("none")){
			autonomousInit();
		}else{
			if(runner == null){
				runner = new CommandListRunner(commandList);
				runner.init();
			}else{
				runner.runList();
			}
		}
		
	}
	
	public void teleopInit(){
		if(runner != null)
			runner.stop();
	}
	
}
