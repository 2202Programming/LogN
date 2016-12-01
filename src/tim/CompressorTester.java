package tim;

import comms.SmartWriter;
import edu.wpi.first.wpilibj.Compressor;
import robot.IControl;

public class CompressorTester extends IControl
{

private Compressor compressor;
	
	public CompressorTester(Compressor compressor)
	{
		this.compressor = compressor;
	}
	
	public void autonomousInit() 
	{
		compressor.setClosedLoopControl(true);	
	}

	public void disabledInit() 
	{
		compressor.setClosedLoopControl(false);
	}
	
	
}
