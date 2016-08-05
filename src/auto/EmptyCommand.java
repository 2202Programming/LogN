package auto;

import drive.IDrive;

public class EmptyCommand implements Command {
	private IDrive drive;
	public EmptyCommand(){
	}
	
	public boolean run(String robotName){
		return false;
	}

}
