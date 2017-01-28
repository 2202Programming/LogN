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
		SensorController SC = SensorController.getInstance();
		SC.registerSensor("FLENCODER", new Encoder(1,1));
		SC.registerSensor("FRENCODER", new Encoder(1,2));
		SC.registerSensor("NAVX", new AHRS(SerialPort.Port.kMXP));
		//TODO add the sensors here
		
		// Creates the global solenoid controller
		SolenoidController SO = SolenoidController.getInstance();
		//Example to add solenoid:
		SO.registerSolenoid("TRIGGER", new DoubleSolenoid(2,3));
		//TODO register the solenoids here

		// Create IMotors for Arcade Drive
		IMotor FL=new JaguarMotor(getInt("FLMOTORPIN"),true);
		IMotor FR=new JaguarMotor(getInt("FRMOTORPIN"),false);
		IMotor BL=new JaguarMotor(getInt("BLMOTORPIN"),true);
		IMotor BR=new JaguarMotor(getInt("BRMOTORPIN"),false);

		Compressor compressor = new Compressor();
		// Create IDrive arcade drive 
		IDrive AD=new ArcadeDrive(FL, FR, BL, BR);
		
		// Create the autonomous command list maker, and command runner
		//CommandListMaker CLM = new CommandListMaker();
		//CommandListRunner CR = new CommandListRunner(CLM.makeList1(),"TIM");  // makes list one for the TIM robot
		
		//Create the IMotors for the Shooter class
		IMotor SL = new JaguarMotor(getInt("SLMOTORPIN"),false);
		IMotor SR = new JaguarMotor(getInt("SRMOTORPIN"),false);
		IMotor SH = new VictorMotor(getInt("SHMOTORPIN"),false);
		
		// Create the class for Tim's shooter
		Shooter S = new Shooter(SL, SR, SH);
		//EnableCompressor compressorTester = new EnableCompressor(compressor);
		temp.put("DRIVE", AD);		
//		temp.put("CR", CR);
//		temp.put("S", S);
		
		return temp;
	}

}
