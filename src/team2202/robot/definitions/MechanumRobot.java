package team2202.robot.definitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

	
	public MechanumRobot(Properties nproperties) {
		super(nproperties);
		// TODO Auto-generated constructor stub
	}


	protected boolean useXML() {
		return false;
	}

	
	protected String loadDefinitionName() {
		return "MECHANUMROBOT";
	}

	
	protected void loadManualDefinitions() {
		robotProperties=new Properties();
		
		// Default Motor Pins
		//port 7 does not work
		robotProperties.setProperty("FRMOTORPIN", "1");//r
		robotProperties.setProperty("BRMOTORPIN", "2");//r
		robotProperties.setProperty("FLMOTORPIN", "3");
		robotProperties.setProperty("BLMOTORPIN", "4");
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
