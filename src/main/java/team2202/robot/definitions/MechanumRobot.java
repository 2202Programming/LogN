package team2202.robot.definitions;

import java.util.HashMap;
import java.util.Map;

import comms.XboxController;
import drive.ArcadeDrive;
import drive.IDrive;
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
		
		XboxController.getXboxController();
		
		// Create map to store public objects
		Map<String, IControl> temp=super.loadControlObjects();
		
		// Creates the global sensor controller
		SensorController SC = SensorController.getInstance();
		//SC.registerSensor("Name", new AHRS(port));
		//TODO add the sensors here
		
		// Creates the global solenoid controller
		SolenoidController SO = SolenoidController.getInstance();
		//Example to add solenoid:

		// Create IMotors for Arcade Drive
		IMotor FL=new JaguarMotor(getInt("FLMOTORPIN"),true);
		IMotor FR=new JaguarMotor(getInt("FRMOTORPIN"),false);
		IMotor BL=new JaguarMotor(getInt("BLMOTORPIN"),true);
		IMotor BR=new JaguarMotor(getInt("BRMOTORPIN"),false);
		// Create IDrive arcade drive 
		IDrive AD=new ArcadeDrive(FL, FR, BL, BR); 
		
		// Create the autonomous command list maker, and command runner
		//CommandListMaker CLM = new CommandListMaker();
		//CommandRunner CR = new CommandRunner(CLM.makeList1(),"TIM");  // makes list one for the TIM robot
		
		//EnableCompressor compressorTester = new EnableCompressor(compressor);
		temp.put("IDrive", AD);		
//		temp.put("CR", CR);
//		temp.put("S", S);
		
		return temp;
	}

}
