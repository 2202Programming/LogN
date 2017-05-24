package team2202.robot.definitions;

import java.util.HashMap;
import java.util.Map;

import comms.XboxController;
import drive.ArcadeDrive;
import drive.IDrive;
import drive.MechanumDrive;
import edu.wpi.first.wpilibj.RobotDrive;
import input.SensorController;
import physicalOutput.SolenoidController;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.JaguarMotor;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;

/**
 * The Tim implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class MechanumRobot extends RobotDefinitionBase {

	
	protected boolean useXML() {
		return false;
	}

	
	protected String loadDefinitionName() {
		return "MECHANUMROBOT";
	}

	
	protected void loadManualDefinitions() {
		_properties=new HashMap<String, String>();
		
		// Default Motor Pins
		//port 7 does not work
		_properties.put("FRMOTORPIN", "1");//r
		_properties.put("BRMOTORPIN", "2");//r
		_properties.put("FLMOTORPIN", "3");
		_properties.put("BLMOTORPIN", "4");
	}

	/***
	 * 
	 * @return  Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {
		
		XboxController controller = XboxController.getXboxController();
		
		// Create map to store public objects
		Map<String, IControl> temp=super.loadControlObjects();
		new MechanumDrive(getInt("FLMOTORPIN"), getInt("BLMOTORPIN"), getInt("FRMOTORPIN"), getInt("BRMOTORPIN"));
		return temp;
	}

}
