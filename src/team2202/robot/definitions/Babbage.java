package team2202.robot.definitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.kauailabs.navx.frc.AHRS;

import LED.LEDController;
import comms.NetworkTables;
import comms.TableNamesEnum;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Ultrasonic;
import input.EncoderMonitor;
import input.SensorController;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.ServoMotor;
import physicalOutput.motors.SparkMotor;
import physicalOutput.motors.TalonSRX;
import robot.Global;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;
import team2202.robot.components.babbage.Climber;
import team2202.robot.components.babbage.CommandListGear;
import team2202.robot.components.babbage.CommandTester;
import team2202.robot.components.babbage.HighGoalTurning;
import team2202.robot.components.babbage.Intake;
import team2202.robot.components.babbage.Shooter;
import team2202.robot.definitions.controls.BabbageControl;

/**
 * The Piper implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class Babbage extends RobotDefinitionBase {

	public Babbage(Properties nproperties) {
		super(nproperties);
		// TODO Auto-generated constructor stub
	}

	protected boolean useXML() {
		return false;
	}

	protected String loadDefinitionName() {
		return "BABBAGE";
	}

	protected void loadManualDefinitions() {
		robotProperties = new Properties();

		// Intake system
		robotProperties.setProperty("LIFTERMOTOR", "2");
		robotProperties.setProperty("INTAKEMOTOR", "4");
		// Default Motor Pins
		robotProperties.setProperty("LEFTMOTORPIN", "0");
		robotProperties.setProperty("RIGHTMOTORPIN", "1");
		// Shooter pins
		robotProperties.setProperty("SHOOTWHEEL", "11");// MainShooterWheel
		robotProperties.setProperty("CHAMBERMOTOR", "5");// Motor to load balls
		robotProperties.setProperty("TURRETMOTOR", "9");// Motor to rotate shooter left and right
		robotProperties.setProperty("HEIGHTMOTOR", "8");// Motor to rotate shooter up and down
		robotProperties.setProperty("AGITATORMOTOR", "6");// Agitates balls
		// Gear holder
		robotProperties.setProperty("GEARMOTOR", "7");
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
		encoder0.setDistancePerPulse(0.0534);
		encoder0.setReverseDirection(false);
		EncoderMonitor encoderMonitor = new EncoderMonitor();
		encoderMonitor.add("ENCODER0", encoder0);

		// TODO add the sensors here
		SensorController sensorController = SensorController.getInstance();
		sensorController.registerSensor("ENCODER0", encoder0);
		sensorController.registerSensor("NAVX", new AHRS(SerialPort.Port.kMXP));
		sensorController.registerSensor("DISTANCESENSOR", new Ultrasonic(7, 8));

		// Create IMotors for Arcade Drive
		IMotor leftMotors = new SparkMotor(getInt("LEFTMOTORPIN"), false);
		IMotor rightMotors = new SparkMotor(getInt("RIGHTMOTORPIN"), true);

		// Create IDrive arcade drive
		IDrive arcadeDrive=new ArcadeDrive(leftMotors, rightMotors);
		temp.put(RobotDefinitionBase.DRIVENAME, arcadeDrive);
		//HighGoalTurning highGoalTurnings=new HighGoalTurning();
		
		//Intake
		IMotor[] intakeMotors= {new SparkMotor(getInt("INTAKEMOTOR"),true)};
		Intake intake=new Intake(intakeMotors);

		//Shooter
		IMotor shooterWheelMotor = new TalonSRX(getInt("SHOOTWHEEL"), true, true);
		ServoMotor turretMotor = new ServoMotor(getInt("TURRETMOTOR"));
		ServoMotor heightMotor = new ServoMotor(getInt("HEIGHTMOTOR"));
		IMotor chamberMotor = new SparkMotor(getInt("CHAMBERMOTOR"), true);
		IMotor agitatorMotor = new SparkMotor(getInt("AGITATORMOTOR"), false);
		//TODO the 5th motor will be the shooter angle motor
		Shooter shooter = new Shooter(shooterWheelMotor, chamberMotor, agitatorMotor, turretMotor, heightMotor);
		temp.put("SHOOTER", shooter);
		HighGoalTurning turning=new HighGoalTurning(turretMotor, heightMotor);
		
		// Gear Holder
		IMotor gearMotor = new SparkMotor(getInt("GEARMOTOR"), false);
		// GearHolder GH = new GearHolder(G);
		
		IMotor climbMotor = new SparkMotor(getInt("LIFTERMOTOR"), true);
		Climber climb = new Climber(climbMotor);
		new CommandListGear();
		new CommandTester();
		
		

		return temp;
	}

}
