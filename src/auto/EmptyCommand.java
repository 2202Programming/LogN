package auto;

public class EmptyCommand implements Command {
	public EmptyCommand(){
	}
	
	public boolean run(String robotName){
		return false;
	}

}
