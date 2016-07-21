package auto;

import drive.IDrive;

public class EmptyCommand implements Command {
	public EmptyCommand(){
	}
	
	public boolean run(String robotName, IDrive drive){
		return false;
	}

}
