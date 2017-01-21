package physicalOutput;

import edu.wpi.first.wpilibj.Compressor;
import robot.IControl;

/**
 * 
 * @author BeastRomulus<br>
 *<b><i>Precondition</i></b>: Have a compressor on the robot<br>
 *<b><i>Postcondition</i></b>: Wont blow up
 */
public class EnableCompressor extends IControl
{

	
	//make sure to change the robot to Tim from Piper next time we build that was ore problem
	
	
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
