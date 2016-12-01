package input;

import com.kauailabs.navx.frc.AHRS;

import comms.DebugMode;
import comms.SmartWriter;
import robot.IControl;

public class NavXTester extends IControl {
	
	public NavXTester(){
		super();
	}
	
	public void teleopInit() {
		
	}
	
	public void teleopPeriodic() {
		AHRS navx = (AHRS)SensorController.getInstance().getSensor("NAVX");
		double navxAngle = navx.getAngle();
		SmartWriter.putD("AngleASDF", navxAngle, DebugMode.FULL);
		
		
	}


}
