package auto;

import drive.IDrive;

public class EmptyCommand implements Command {
	private IDrive drive;
	public EmptyCommand(){
	}
	
	public void init(IDrive drive){
		this.drive = drive;
	}
	
	public boolean run(String robotName){
		return false;
	}

}
