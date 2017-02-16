package babbage;

import auto.CommandList;
import auto.CommandListRunner;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class CommandTester extends IControl
{

	private CommandList commandList;
	private BabbageControl controllers = (BabbageControl) Global.controllers;
	private DriverStation ds;
	
public CommandTester() 
{
	/*
	commandList = BabbageAutoLists.centerBlue();
	new CommandListRunner(commandList);
	*/
	
	boolean blueSide = false;
	if (ds.getAlliance() == Alliance.Red) {
		blueSide = false;
	}
	else {
		blueSide = true;
	}
	
	if (!blueSide) {
		if (controllers.autoFieldPosition1()) {			
			commandList = BabbageAutoLists.centerRed();
		}
		if (controllers.autoFieldPosition2()) {			
			commandList = BabbageAutoLists.boilerRed();
		}
		if (controllers.autoFieldPosition0()) {			
			commandList = BabbageAutoLists.notBoilerRed();
		}
	}
	
	if (blueSide) {
		if (controllers.autoFieldPosition1()) {			
			commandList = BabbageAutoLists.centerBlue();
		}
		if (controllers.autoFieldPosition0()) {			
			commandList = BabbageAutoLists.boilerBlue();
		}
		if (controllers.autoFieldPosition2()) {			
			commandList = BabbageAutoLists.notBoilerBlue();
		}
	}
	
	new CommandListRunner(commandList);
}
	
	
public void autonomousInit() 
{
	
}
	
	
	
	
	
	
	
}
