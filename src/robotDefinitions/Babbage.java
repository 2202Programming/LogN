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
		_properties.put("LIFTERMOTOR", "5");
		_properties.put("INTAKEMOTOR", "0");
		// Default Motor Pins
		_properties.put("FLMOTORPIN", "3");
		_properties.put("BLMOTORPIN", "4");
		_properties.put("FRMOTORPIN", "2");
		_properties.put("BRMOTORPIN", "1");
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
		Relay red = new Relay(1);
		Relay blue = new Relay(2);
		Relay green = new Relay(0);
		LEDController ledController = new LEDController();
		ledController.addLED(red, LEDActiveState.RED);
		ledController.addLED(blue, LEDActiveState.BLUE);
		ledController.addLED(green, LEDActiveState.ENABLED);
		
		BabbageControl babbageControl = new BabbageControl();
		temp.put("CONTROL", babbageControl);
		Global.controllers = babbageControl ;
		NetworkTables visionTable = new NetworkTables(TableNamesEnum.VisionTable);
		temp.put("NT", visionTable);

		// TODO add the sensors here
		SensorController sensorController = SensorController.getInstance();

		// Create IMotors for Arcade Drive
		IMotor FL = new SparkMotor(getInt("FLMOTORPIN"), false);
		IMotor FR = new SparkMotor(getInt("FRMOTORPIN"), true);
		IMotor BL = new SparkMotor(getInt("BLMOTORPIN"), false);
		IMotor BR = new SparkMotor(getInt("BRMOTORPIN"), true);

		// Create IDrive arcade drive
		IDrive arcadeDrive=new ArcadeDrive(FL, FR, BL, BR);
		//HighGoalTurning highGoalTurnings=new HighGoalTurning();
		
		//Intake
		IMotor[] intakeMotors= {new SparkMotor(getInt("INTAKEMOTOR"),false)};
		Intake intake=new Intake(intakeMotors);

		//Shooter
		IMotor shooterWheelMotor = new TalonSRX(getInt("SHOOTWHEEL"), false, false);
		ServoMotor turretMotor = new ServoMotor(getInt("TURRETMOTOR"));
		IMotor chamberMotor = new SparkMotor(getInt("CHAMBERMOTOR"), false);
		//TODO the fourth motor will be the shooter angle motor
		Shooter shooter = new Shooter(shooterWheelMotor, chamberMotor, turretMotor, turretMotor);

		//Gear Holder
		IMotor gearMotor = new SparkMotor(getInt("GEARMOTOR"), false);
		//GearHolder GH = new GearHolder(G);

		temp.put("DRIVE", arcadeDrive);

		return temp;
	}

}
