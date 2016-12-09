package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.SerialPort;
import input.NavXTester;
import input.SensorController;
import physicalOutput.IMotor;
import physicalOutput.SparkMotor;
import robot.IControl;

/**
 * The Piper implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class Piper extends RobotDefinitionBase {

	protected boolean useXML() {
		return false;
	}

	protected String loadDefinitionName() {
		return "PIPER";
	}

	protected void loadManualDefinitions() {
		_properties=new HashMap<String, String>();

		// Default Motor Pins
		_properties.put("FLMOTORPIN", "3");// PWM3
		_properties.put("BLMOTORPIN", "4");// PWM4
		_properties.put("FRMOTORPIN", "1");// PWM1
		_properties.put("BRMOTORPIN", "2");// PWM2
		_properties.put("SFLMOTORPIN", "8");// Shooter front left
		_properties.put("SBLMOTORPIN", "8");// Shooter back left
		_properties.put("SFRMOTORPIN", "7");// Shooter front right
		_properties.put("SBRMOTERPIN", "7");// Shooter back left
	}

	/***
	 * 
	 * @return Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {

		// Create map to store public objects
		Map<String, IControl> iControlMap=super.loadControlObjects();

		// TODO add the sensors here
		/*
		 * // Creates the global solenoid controller SolenoidController SO =
		 * SolenoidController.getInstance(); SO.registerSolenoid("TRIGGER", new
		 * DoubleSolenoid(1,1)); //TODO register the solenoids here
		 */

		// Create IMotors for Arcade Drive
		IMotor FL=new SparkMotor(getInt("FLMOTORPIN"), false);
		IMotor FR=new SparkMotor(getInt("FRMOTORPIN"), true);
		IMotor BL=new SparkMotor(getInt("BLMOTORPIN"), false);
		IMotor BR=new SparkMotor(getInt("BRMOTORPIN"), true);

		//Add motors to the global hashmap
		iControlMap.put("FRONT_LEFT_MOTOR", FL);
		iControlMap.put("FRONT_RIGHT_MOTOR", FR);
		iControlMap.put("BACK_LEFT_MOTOR", BL);
		iControlMap.put("BACK_RIGHT_MOTOR", BR);

		// Create IDrive arcade drive I dont know why we cast it as a IDrive
		// though
		IDrive AD=new ArcadeDrive(FL, FR, BL, BR);
		iControlMap.put("ARCADE_DRIVE", AD);

		SensorController SC=SensorController.getInstance();
		SC.registerSensor("NAVX", new AHRS(SerialPort.Port.kMXP));

		new NavXTester();

		// Create the autonomous command list maker, and command runner
		// CommandListMaker CLM = new CommandListMaker(AD);
		// CommandRunner CR = new CommandRunner(CLM.makeList1(),"PIPER"); //
		// makes list one for the TIM robot

		// Create the IMotors for the Shooter class
		// IMotor SL = new SparkMotor(getInt("SLMOTORPIN"),false);
		// IMotor SR = new SparkMotor(getInt("SRMOTORPIN"),false);

		// temp.put("AD", AD);
		// temp.put("CR", CR);

		return iControlMap;
	}

}