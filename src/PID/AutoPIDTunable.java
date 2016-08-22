package PID;

public interface AutoPIDTunable {
	
	void startReset();
	void setToRandomState();
	boolean getResetFinished();
		
	boolean getError();
	void setValue();
}
