package team2202.robot.definitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.kauailabs.navx.frc.AHRS;

import comms.XboxController;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort;
import input.SensorController;
import physicalOutput.SolenoidController;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.JaguarMotor;
import physicalOutput.motors.VictorMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;
import team2202.robot.components.tim.Shooter;
import team2202.robot.definitions.controls.TimControl;

/**
 * The Tim implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class Tim extends RobotDefinitionBase {

	
	public Tim(Properties nproperties) {
		super(nproperties);
		// TODO Auto-generated constructor stub
	}


	protected boolean useXML() {
		return false;
	}

	
	protected String loadDefinitionName() {
		return "TIM";
	}

	
	protected void loadManualDefinitions() {
		robotProperties=new Properties();
		
		// Default Motor Pins
		//port 7 does not work
		robotProperties.setProperty("FRMOTORPIN", "1");//r
		robotProperties.setProperty("BRMOTORPIN", "2");//r
		robotProperties.setProperty("FLMOTORPIN", "3");
		robotProperties.setProperty("BLMOTORPIN", "4");
		robotProperties.setProperty("SLMOTORPIN", "5");//TODO put actual pins here
		robotProperties.setProperty("SRMOTORPIN", "6");
		robotProperties.setProperty("SHMOTORPIN", "8");
	}

	/***
	 * 
	 * @return  Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {
		Global.controllers=new TimControl();
		XboxController.getXboxController();
		
		// Create map to store public objects
		Map<String, IControl> temp=super.loadControlObjects();
		
		// Creates the global sensor controller
		SensorController sensorController = SensorController.getInstance();
		//sensorController.registerSensor("FLENCODER", new Encoder(1,1));//This was a problem, so I commented it out
		//sensorController.registerSensor("FRENCODER", new Encoder(1,2));//This uses the same resource as the above one
		sensorController.registerSensor("NAVX", new AHRS(SerialPort.Port.kMXP));
		//TODO add the sensors here
		
		// Creates the global solenoid controller
		SolenoidController solenoidController = SolenoidController.getInstance();
		//Example to add solenoid:
		solenoidController.registerSolenoid("TRIGGER", new DoubleSolenoid(2,3));
		//TODO register the solenoids here

		// Create IMotors for Arcade Drive
		IMotor FL=new JaguarMotor(getInt("FLMOTORPIN"),true);
		IMotor FR=new JaguarMotor(getInt("FRMOTORPIN"),false);
		IMotor BL=new JaguarMotor(getInt("BLMOTORPIN"),true);
		IMotor BR=new JaguarMotor(getInt("BRMOTORPIN"),false);

		new Compressor();

		IDrive arcadeDrive=new ArcadeDrive(FL, FR, BL, BR);
		
		//Create the IMotors for the Shooter class
		IMotor shooterLeftMotor = new JaguarMotor(getInt("SLMOTORPIN"),false);
		IMotor shooterRightMotor = new JaguarMotor(getInt("SRMOTORPIN"),false);
		IMotor shooterHeightMotor = new VictorMotor(getInt("SHMOTORPIN"),false);
		
		// Create the class for Tim's shooter
		DigitalInput upperLimitSwitch=new DigitalInput(6);
		DigitalInput lowerLimitSwitch=new DigitalInput(7);
		Shooter S = new Shooter(shooterLeftMotor, shooterRightMotor, shooterHeightMotor, upperLimitSwitch, lowerLimitSwitch);
		temp.put(RobotDefinitionBase.DRIVENAME, arcadeDrive);		
		
		return temp;
	}

}
