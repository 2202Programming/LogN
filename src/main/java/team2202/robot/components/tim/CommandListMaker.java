package team2202.robot.components.tim;

import auto.CommandList;
import auto.commands.EmptyCommand;
import auto.stopConditions.TimerStopCondition;

//Every robots package will have its own CommandListMaker with its unique command list options
public class CommandListMaker {
		
	public static CommandList makeList1(){
		CommandList list = new CommandList();
		list.addCommand(new EmptyCommand(new TimerStopCondition(5000)));
		return list;
	}
}
