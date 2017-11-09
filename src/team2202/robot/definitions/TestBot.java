package team2202.robot.definitions;

import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import comms.SmartWriter;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import input.EncoderMonitor;
import input.SensorController;
import physicalOutput.SolenoidController;
import physicalOutput.motors.ChainMotor;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.SparkMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;
import team2202.robot.components.piper.Intake;
import team2202.robot.components.piper.Shooter;
import team2202.robot.definitions.controls.PiperControl;

/**
 * The Piper implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class TestBot extends RobotDefinitionBase {

	protected boolean useXML() {
		return false;
	}

	protected String loadDefinitionName() {
		return "TEST";
	}

	protected void loadManualDefinitions() {
		_properties=new HashMap<String, String>();

		// Default Motor Pins
		_properties.put("FLMOTORPIN", "3");// PWM3
		_properties.put("BLMOTORPIN", "4");// PWM4
		_properties.put("FRMOTORPIN", "1");// PWM1
		_properties.put("BRMOTORPIN", "2");// PWM2
		_properties.put("SFLMOTORPIN", "6");// Shooter front left
		_properties.put("SBLMOTORPIN", "6");// Shooter back left
		_properties.put("SFRMOTORPIN", "7");// Shooter front right
		_properties.put("SBRMOTERPIN", "7");// Shooter back left
		_properties.put("INTAKEMOTOR", "5");
	}

	/***
	 * 
	 * @return Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {

		SmartWriter.putS("Robot is test...", "asdf");
		// Create map to store public objects
		Map<String, IControl> iControlMap=super.loadControlObjects();
		
		Global.controllers = new PiperControl();

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
		ArcadeDrive newDrive=new ArcadeDrive(FL, FR, BL, BR);
		iControlMap.put("new drive", newDrive);
		

		//Encoder stuff
		Encoder encoder0 =new Encoder(0, 1);
		Encoder encoder1 =  new Encoder(2, 3);
		encoder0.setDistancePerPulse(0.058);
		encoder1.setDistancePerPulse(0.06529);
		//EncoderMonitor encoderMonitor = new EncoderMonitor();
		//encoderMonitor.add("ENCODER0", encoder0);
		//encoderMonitor.add("ENCODER1", encoder1);
		
		SensorController sensorController=SensorController.getInstance();
		sensorController.registerSensor("ENCODER0", encoder0);
		sensorController.registerSensor("ENCODER1", encoder1);
		sensorController.registerSensor("NAVX", new AHRS(SerialPort.Port.kMXP));
		
		SolenoidController solenoidController = SolenoidController.getInstance();
		solenoidController.registerSolenoid("intakeSolenoid", new DoubleSolenoid(4,5));
		
		
		
		
		return iControlMap;
	}

}
