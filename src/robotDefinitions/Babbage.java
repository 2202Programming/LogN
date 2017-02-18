package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import LED.LEDActiveState;
import LED.LEDController;
import babbage.Intake;
import babbage.Shooter;
import comms.NetworkTables;
import comms.TableNamesEnum;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
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
		_properties.put("AGITATORMOTOR", "7");//Agitates balls
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
		Global.controllers = babbageControl ;
		NetworkTables visionTable = new NetworkTables(TableNamesEnum.VisionTable);
		temp.put("NT", visionTable);

		// TODO add the sensors here
		SensorController sensorController = SensorController.getInstance();

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
		//TODO the fourth motor will be the shooter angle motor
		//Shooter shooter = new Shooter(shooterWheelMotor, chamberMotor, agitatorMotor, turretMotor, turretMotor);

		//Gear Holder
		IMotor gearMotor = new SparkMotor(getInt("GEARMOTOR"), false);
		//GearHolder GH = new GearHolder(G);

		temp.put("DRIVE", arcadeDrive);

		return temp;
	}

}
