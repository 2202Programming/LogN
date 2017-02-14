package babbage;

import auto.CommandList;
import auto.CommandListRunner;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class CommandTester extends IControl
{

	private CommandList commandList;
	
public CommandTester() 
{
	commandList = BabbageAutoLists.centerBlue();
	new CommandListRunner(commandList);
}
	
	
public void autonomousInit() 
{
	
}
	
	
	
	
	
	
	
}
