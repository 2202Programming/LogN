package team2202.robot.definitions;

import java.util.HashMap;
import java.util.Map;

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
		_properties.put("LEFTMOTORPIN", "0");
		_properties.put("RIGHTMOTORPIN", "1");
		// Shooter pins
		_properties.put("SHOOTWHEEL", "11");// MainShooterWheel
		_properties.put("CHAMBERMOTOR", "5");// Motor to load balls
		_properties.put("TURRETMOTOR", "9");// Motor to rotate shooter left and right
		_properties.put("HEIGHTMOTOR", "8");// Motor to rotate shooter up and down
		_properties.put("AGITATORMOTOR", "6");// Agitates balls
		// Gear holder
		_properties.put("GEARMOTOR", "7");
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
		IMotor agitatorMotor = new SparkMotor(getInt("AGITATORMOTOR"), false);
		//TODO the 5th motor will be the shooter angle motor
		Shooter shooter = new Shooter(shooterWheelMotor, agitatorMotor);
		temp.put("SHOOTER", shooter);
		
		IMotor climbMotor = new SparkMotor(getInt("LIFTERMOTOR"), true);
		Climber climb = new Climber(climbMotor);
		new CommandListGear();
		new CommandTester();
		
		

		return temp;
	}

}
