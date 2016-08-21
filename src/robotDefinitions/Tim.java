package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import auto.*;
import tim.*;
import drive.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import physicalOutput.*;
import robot.*;
import input.*;

/**
 * The Tim implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class Tim extends RobotDefinitionBase {

	@Override
	protected boolean useXML() {
		return false;
	}

	@Override
	protected String loadDefinitionName() {
		return "TIM";
	}

	@Override
	protected void loadManualDefinitions() {
		_properties=new HashMap<String, String>();
		
		// Default Motor Pins
		_properties.put("FLMOTORPIN", "0");
		_properties.put("BLMOTORPIN", "2");
		_properties.put("FRMOTORPIN", "1");
		_properties.put("BRMOTORPIN", "3");
		_properties.put("SLMOTORPIN", "4");//TODO put actual pins here
		_properties.put("SRMOTORPIN", "5");
		_properties.put("SHMOTORPIN", "6");
	}

	/***
	 * 
	 * @return  Control object map for Tim
	 */
	public Map<String, IControl> loadControlObjects() {
		
		// Create map to store public objects
		Map<String, IControl> temp=super.loadControlObjects();
		
		// Creates the global sensor controller
		SensorController SC = SensorController.getInstance();
		SC.registerSensor("example", new Encoder(1,1));
		//TODO add the sensors here
		
		// Creates the global solenoid controller
		SolenoidController SO = SolenoidController.getInstance();
		SO.registerSolenoid("TRIGGER", new DoubleSolenoid(1,1));
		//TODO register the solenoids here

		// Create IMotors for Arcade Drive
		IMotor FL=new SparkMotor(getInt("FLMOTORPIN"));
		IMotor FR=new SparkMotor(getInt("FRMOTORPIN"));
		IMotor BL=new SparkMotor(getInt("BLMOTORPIN"));
		IMotor BR=new SparkMotor(getInt("BRMOTORPIN"));

		// Create IDrive arcade drive I dont know why we cast it as a IDrive though
		IDrive AD=new ArcadeDrive(FL, FR, BL, BR);
		
		// Create the autonomous command list maker, and command runner
		CommandListMaker CLM = new CommandListMaker(AD);
		CommandRunner CR = new CommandRunner(CLM.makeList1(),"TIM");  // makes list one for the TIM robot
		
		//Create the IMotors for the Shooter class
		IMotor SL = new SparkMotor(getInt("SLMOTORPIN"));
		IMotor SR = new SparkMotor(getInt("SRMOTORPIN"));
		IMotor SH = new SparkMotor(getInt("SHMOTORPIN"));
		
		// Create the class for Tim's shooter
		Shooter S = new Shooter(SL, SR, SH);
		
		temp.put("AD", AD);		
		temp.put("CR", CR);
		temp.put("S", S);

		return temp;
	}

}
