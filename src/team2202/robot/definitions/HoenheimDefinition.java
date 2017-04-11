package team2202.robot.definitions;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map;

import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import physicalOutput.EnableCompressor;
import physicalOutput.motors.ChainMotor;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.IMotorPIDOutput;
import physicalOutput.motors.TalonMotor;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;
import team2202.robot.components.hoenheim.Intake;
import team2202.robot.components.hoenheim.Shooter;

public class HoenheimDefinition extends RobotDefinitionBase {

	@Override
	protected boolean useXML() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String loadDefinitionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void loadManualDefinitions() {
		// TODO Auto-generated method stub
		
	}
	
	public Map<String,IControl> loadControlObjects(){
		Map<String,IControl> toReturn = super.loadControlObjects();
		new EnableCompressor(new Compressor());
		TalonMotor frontLeft = new TalonMotor(3, false);
		TalonMotor frontRight = new TalonMotor(1, true);
		TalonMotor backLeft = new TalonMotor(4, false);
		TalonMotor backRight = new TalonMotor(2, true);
		IDrive drive = new ArcadeDrive(frontLeft, frontRight, backLeft, backRight);
		
		toReturn.put(DRIVENAME, drive);
		
		TalonMotor intakeMotor = new TalonMotor(7, false);
		DigitalInput intakeLimitSwitch = new DigitalInput(3);
		Intake intake = new Intake(intakeMotor, intakeLimitSwitch); 
		
		ArrayList<IMotor> shooterMotors = new ArrayList<IMotor>();
		shooterChain.add(TalonMotor shooter1Left = new Talon());
		shooterChain.add(TalonMotor shooter1Right = new Talon());
		shooterChain.add(TalonMotor shooter2Left = new Talon());
		shooterChain.add(TalonMotor shooter2Right = new Talon());
		ChainMotor shooterChain = new ChainMotor(shooterMotors);
		IMotorPIDOutput shooterChainControl = new IMotorPIDOutput(shooterChain);
		
		
		Shooter shooter = new Shooter(shooterChainControl, );
		
		
		//
		//upper shooter limit switch: DIO 1
		//lower shooter limit switch: DIO 0
		
		//
		
		return toReturn;

	}

}
