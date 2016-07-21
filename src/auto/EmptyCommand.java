package auto;

import drive.ArcadeDrive;

public class EmptyCommand implements Command {
	public EmptyCommand(){
	}
	
	public boolean run(String robotName, ArcadeDrive drive){
		return false;
	}

}
