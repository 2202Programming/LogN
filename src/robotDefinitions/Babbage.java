package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import babbage.CommandTester;
import babbage.HighGoalTurning;
import babbage.Intake;
import babbage.Shooter;
import comms.NetworkTables;
import comms.TableNamesEnum;
import drive.ArcadeDrive;
import drive.IDrive;
import input.SensorController;
import physicalOutput.IMotor;
import physicalOutput.ServoMotor;
import physicalOutput.SparkMotor;
import physicalOutput.TalonSRX;
import robot.Global;
import robot.IControl;

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
		_properties = new HashMap<String, String>();

		// Default Motor Pins
		_properties.put("FLMOTORPIN", "2");// PWM3
		_properties.put("BLMOTORPIN", "3");// PWM4
		_properties.put("FRMOTORPIN", "1");// PWM1
		_properties.put("BRMOTORPIN", "0");// PWM2
		// Shooter pins
		_properties.put("SHOOTWHEEL", "11");// MainShooterWheel
		_properties.put("CHAMBERMOTOR", "7");// Motor to load balls
		_properties.put("TURRETMOTOR", "9");// Motor to rotate shooter
		// Gear holder
		_properties.put("GEARMOTOR", "6");
		//Intake Motors
		_properties.put("INTAKE1", "4");
		_properties.put("INTAKE2", "5");
	}

	/***
	 * 
	 * @return Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {

		// Create map to store public objects
		Map<String, IControl> temp = super.loadControlObjects();
		BabbageControl babbageControl = new BabbageControl();
		temp.put("CONTROL", babbageControl);
		Global.controllers = babbageControl ;
		NetworkTables visionTable = new NetworkTables(TableNamesEnum.VisionTable);
		temp.put("NT", visionTable);

		// TODO add the sensors here
		SensorController sensorController = SensorController.getInstance();

		/*
		 * // Creates the global solenoid controller SolenoidController SO =
		 * SolenoidController.getInstance(); SO.registerSolenoid("TRIGGER", new
		 * DoubleSolenoid(1,1)); //TODO register the solenoids here
		 */

		// Create IMotors for Arcade Drive
		IMotor FL = new SparkMotor(getInt("FLMOTORPIN"), false);
		IMotor FR = new SparkMotor(getInt("FRMOTORPIN"), true);
		IMotor BL = new SparkMotor(getInt("BLMOTORPIN"), false);
		IMotor BR = new SparkMotor(getInt("BRMOTORPIN"), true);

		// Create IDrive arcade drive I don't know why we cast it as a IDrive though
		IDrive arcadeDrive=new ArcadeDrive(FL, FR, BL, BR);
		HighGoalTurning highGoalTurnings=new HighGoalTurning();
		
		
		IMotor[] intakeMotors= {new SparkMotor(getInt("INTAKE1"), true),new SparkMotor(getInt("INTAKE2"), true)};
		Intake intake=new Intake(intakeMotors);
		
		// Create the autonomous command list maker, and command runner
		// CommandListMaker CLM = new CommandListMaker(AD);
		// CommandListRunner CR = new
		// CommandListRunner(CLM.makeList1(),"PIPER"); // makes list one for the
		// TIM robot

		// Create the IMotors for the Shooter class
		// IMotor SL = new SparkMotor(getInt("SLMOTORPIN"),false);
		// IMotor SR = new SparkMotor(getInt("SRMOTORPIN"),false);

		// TODO put real motors
		IMotor shooterWheelMotor = new TalonSRX(getInt("SHOOTWHEEL"), false, false);
		ServoMotor turretMotor = new ServoMotor(getInt("TURRETMOTOR"));
		IMotor chamberMotor = new SparkMotor(getInt("CHAMBERMOTOR"), false);
		Shooter shooter = new Shooter(shooterWheelMotor, chamberMotor, turretMotor, turretMotor);

		
		
//		CommandTester commandTester = new CommandTester();
		
		
		
		IMotor gearMotor = new SparkMotor(getInt("GEARMOTOR"), false);
		//GearHolder GH = new GearHolder(G);

		// temp.put("DRIVE", AD);
		// temp.put("CR", CR);

		return temp;
	}

}
