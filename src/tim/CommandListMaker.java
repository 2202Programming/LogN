package tim;

import auto.CommandList;
import auto.DriveCommand;
import drive.IDrive;
import robot.Global;

//Every robots package will have its own CommandListMaker with its unique command list options
//Only one command list should be made
public class CommandListMaker {
	private CommandList list;
	private IDrive drive;
	
	public CommandListMaker(){
		drive = (IDrive) Global.controlObjects.get("IDrive");
		list = new CommandList();
	}
	
	public CommandList makeList1(){
		list.addCommand(new DriveCommand(1, 100, drive));
		return list;
	}
	
	public CommandList makeList2(){
		return list;
	}
}
