package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import auto.CommandRunner;
import comms.NetworkTables;
import drive.ArcadeDrive;
import drive.IDrive;
import physicalOutput.IMotor;
import physicalOutput.SparkMotor;
import robot.IControl;
import tim.CommandListMaker;

/**
 * The Piper implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class Babbage extends RobotDefinitionBase {

	
	protected boolean useXML() {
		return false;
	}

	
	protected String loadDefinitionName() {
		return "BABBAGE";
	}

	
	protected void loadManualDefinitions() {
		_properties=new HashMap<String, String>();
		
		// Default Motor Pins
		_properties.put("FLMOTORPIN", "2");//PWM3
		_properties.put("BLMOTORPIN", "3");//PWM4
		_properties.put("FRMOTORPIN", "1");//PWM1
		_properties.put("BRMOTORPIN", "0");//PWM2
	}

	/***
	 * 
	 * @return  Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {
		
		// Create map to store public objects
		Map<String, IControl> temp=super.loadControlObjects();
		
		//Makes the network table for vision
		NetworkTables visionTable = new NetworkTables("VisionTable");
		temp.put("NT", visionTable);
		
		//TODO add the sensors here
		/*
		// Creates the global solenoid controller
		SolenoidController SO = SolenoidController.getInstance();
		SO.registerSolenoid("TRIGGER", new DoubleSolenoid(1,1));
		//TODO register the solenoids here
		 */
		
		// Create IMotors for Arcade Drive
		IMotor FL=new SparkMotor(getInt("FLMOTORPIN"),false);
		IMotor FR=new SparkMotor(getInt("FRMOTORPIN"),true);
		IMotor BL=new SparkMotor(getInt("BLMOTORPIN"),false);
		IMotor BR=new SparkMotor(getInt("BRMOTORPIN"),true);

		// Create IDrive arcade drive I dont know why we cast it as a IDrive though
		IDrive AD=new ArcadeDrive(FL, FR, BL, BR);
		
		// Create the autonomous command list maker, and command runner
//		CommandListMaker CLM = new CommandListMaker(AD);
//		CommandRunner CR = new CommandRunner(CLM.makeList1(),"PIPER");  // makes list one for the TIM robot
		
		//Create the IMotors for the Shooter class
//		IMotor SL = new SparkMotor(getInt("SLMOTORPIN"),false);
//		IMotor SR = new SparkMotor(getInt("SRMOTORPIN"),false);
		
		
		
		
		temp.put("DRIVE", AD);		
//		temp.put("CR", CR);
		

		return temp;
	}

}
