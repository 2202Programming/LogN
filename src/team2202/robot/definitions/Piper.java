package team2202.robot.definitions;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import babbage.CommandTester;
import comms.SmartWriter;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import input.EncoderMonitor;
import input.NavXTester;
import input.SensorController;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.SparkMotor;
import piper.CommandListGear;
import robot.Global;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;
import team2202.robot.definitions.controls.PiperControl;

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

		SmartWriter.putS("Robot is piper...", "asdf");
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

		// Create IDrive arcade drive I dont know why we cast it as a IDrive
		// though
		IDrive arcadeDrive=new ArcadeDrive(FL, FR, BL, BR);
		iControlMap.put("DRIVE", arcadeDrive);

		//Encoder stuff
		Encoder encoder0 =new Encoder(0, 1);
		Encoder encoder1 =  new Encoder(2, 3);
		encoder0.setDistancePerPulse(0.058);
		encoder1.setDistancePerPulse(0.06529);
		EncoderMonitor encoderMonitor = new EncoderMonitor();
		encoderMonitor.add("ENCODER0", encoder0);
		encoderMonitor.add("ENCODER1", encoder1);
		
		SensorController sensorController=SensorController.getInstance();
		sensorController.registerSensor("ENCODER0", encoder0);
		sensorController.registerSensor("ENCODER1", encoder1);
		sensorController.registerSensor("NAVX", new AHRS(SerialPort.Port.kMXP));

//		new NavXTester();
		//new NavXPIDTunable();
		//new CommandListRunnerDoNotKeepItSucks();
		
		
		// Create the autonomous command list maker, and command runner
		// CommandListMaker CLM = new CommandListMaker(AD);
		// CommandListRunner CR = new CommandListRunner(CLM.makeList1(),"PIPER"); //
		// makes list one for the TIM robot

		// Create the IMotors for the Shooter class
		// IMotor SL = new SparkMotor(getInt("SLMOTORPIN"),false);
		// IMotor SR = new SparkMotor(getInt("SRMOTORPIN"),false);

		// temp.put("AD", AD);
		// temp.put("CR", CR);

		return iControlMap;
	}

}
