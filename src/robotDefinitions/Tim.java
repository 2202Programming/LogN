package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import auto.CommandListRunner;
import comms.XboxController;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import input.NavXTester;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import input.SensorController;
import physicalOutput.IMotor;
import physicalOutput.JaguarMotor;
import physicalOutput.SolenoidController;
import physicalOutput.VictorMotor;
import robot.IControl;
import tim.CommandListMaker;
import tim.Shooter;

/**
 * The Tim implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class Tim extends RobotDefinitionBase {

	
	protected boolean useXML() {
		return false;
	}

	
	protected String loadDefinitionName() {
		return "TIM";
	}

	
	protected void loadManualDefinitions() {
		_properties=new HashMap<String, String>();
		
		// Default Motor Pins
		//port 7 does not work
		_properties.put("FRMOTORPIN", "1");//r
		_properties.put("BRMOTORPIN", "2");//r
		_properties.put("FLMOTORPIN", "3");
		_properties.put("BLMOTORPIN", "4");
		_properties.put("SLMOTORPIN", "5");//TODO put actual pins here
		_properties.put("SRMOTORPIN", "6");
		_properties.put("SHMOTORPIN", "8");
	}

	/***
	 * 
	 * @return  Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {
		
		XboxController.getXboxController();
		
		// Create map to store public objects
		Map<String, IControl> temp=super.loadControlObjects();
		
		// Creates the global sensor controller
		SensorController sensorController = SensorController.getInstance();
		sensorController.registerSensor("FLENCODER", new Encoder(1,1));
		sensorController.registerSensor("FRENCODER", new Encoder(1,2));
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
		Shooter S = new Shooter(shooterLeftMotor, shooterRightMotor, shooterHeightMotor);
		temp.put("DRIVE", arcadeDrive);		
		
		return temp;
	}

}
