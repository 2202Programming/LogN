package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import LED.LEDController;
import babbage.Climber;
import babbage.Intake;
import comms.NetworkTables;
import comms.TableNamesEnum;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import input.EncoderMonitor;
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

		// Intake system
		_properties.put("LIFTERMOTOR", "2");
		_properties.put("INTAKEMOTOR", "4");
		// Default Motor Pins
		_properties.put("LEFTMOTORPIN", "1");
		_properties.put("RIGHTMOTORPIN", "0");
		// Shooter pins
		_properties.put("SHOOTWHEEL", "11");// MainShooterWheel
		_properties.put("CHAMBERMOTOR", "8");// Motor to load balls
		_properties.put("TURRETMOTOR", "9");// Motor to rotate shooter
		_properties.put("AGITATORMOTOR", "7");// Agitates balls
		// Gear holder
		_properties.put("GEARMOTOR", "6");
	}

	/***
	 * 
	 * @return Control object map for Babbage
	 */
	public Map<String, IControl> loadControlObjects() {

		// Create map to store public objects
		Map<String, IControl> temp = super.loadControlObjects();
		//most important class goes at the front
		LEDController ledController = new LEDController();
		
		BabbageControl babbageControl = new BabbageControl();
		temp.put("CONTROL", babbageControl);
		Global.controllers = babbageControl;
		NetworkTables visionTable = new NetworkTables(TableNamesEnum.VisionTable);
		temp.put("NT", visionTable);

		// Encoders
		Encoder encoder0 = new Encoder(0, 1);
		Encoder encoder1 = new Encoder(2, 3);
		EncoderMonitor encoderMonitor = new EncoderMonitor();
		encoderMonitor.add("ENCODER0", encoder0);
		encoderMonitor.add("ENCODER1", encoder1);

		// TODO add the sensors here
		SensorController sensorController = SensorController.getInstance();
		sensorController.registerSensor("ENCODER0", encoder0);
		sensorController.registerSensor("ENCODER1", encoder1);

		// Create IMotors for Arcade Drive
		IMotor leftMotors = new SparkMotor(getInt("LEFTMOTORPIN"), false);
		IMotor rightMotors = new SparkMotor(getInt("RIGHTMOTORPIN"), true);

		// Create IDrive arcade drive
		IDrive arcadeDrive=new ArcadeDrive(leftMotors, rightMotors);
		//HighGoalTurning highGoalTurnings=new HighGoalTurning();
		
		//Intake
		IMotor[] intakeMotors= {new SparkMotor(getInt("INTAKEMOTOR"),true)};
		Intake intake=new Intake(intakeMotors);

		//Shooter
		IMotor shooterWheelMotor = new TalonSRX(getInt("SHOOTWHEEL"), false, false);
		ServoMotor turretMotor = new ServoMotor(getInt("TURRETMOTOR"));
		IMotor chamberMotor = new SparkMotor(getInt("CHAMBERMOTOR"), false);
		IMotor agitatorMotor = new SparkMotor(getInt("AGITATORMOTOR"), false);
		//TODO the 5th motor will be the shooter angle motor
		//Shooter shooter = new Shooter(shooterWheelMotor, chamberMotor, agitatorMotor, turretMotor, turretMotor);

		// Gear Holder
		IMotor gearMotor = new SparkMotor(getInt("GEARMOTOR"), false);
		// GearHolder GH = new GearHolder(G);
		
		IMotor climbMotor = new SparkMotor(getInt("LIFTERMOTOR"), true);
		Climber climb = new Climber(climbMotor);

		temp.put("DRIVE", arcadeDrive);

		return temp;
	}

}
