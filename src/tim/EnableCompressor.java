package tim;

import edu.wpi.first.wpilibj.Compressor;
import robot.IControl;

public class EnableCompressor extends IControl
{

	
	//make sure to change the robot to Tim from Piper next time we build that was are problem
	
	
private Compressor compressor;
	
	public EnableCompressor (Compressor compressor)
	{
		this.compressor = compressor;
	}
	
	public void autonomousInit() 
	{
		compressor.start();
	}

	public void disabledInit() 
	{
		compressor.stop();
	}
	
	
}
