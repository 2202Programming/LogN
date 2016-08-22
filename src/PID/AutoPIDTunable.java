package PID;

public interface AutoPIDTunable {
	
	void startReset();
	void setToRandomState();
	boolean getResetFinished();
		
	double getError();
	void setValue(double turnValue);
}
