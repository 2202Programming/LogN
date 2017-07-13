package team2202.robot.definitions;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import comms.XboxController;
import drive.ArcadeDrive;
import drive.IDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import physicalOutput.EnableCompressor;
import physicalOutput.SolenoidController;
import physicalOutput.motors.ChainMotor;
import physicalOutput.motors.IMotor;
import physicalOutput.motors.IMotorPIDOutput;
import physicalOutput.motors.TalonMotor;
import robot.Global;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;
import team2202.robot.components.hoenheim.Intake;
import team2202.robot.components.hoenheim.Shooter;
import team2202.robot.definitions.controls.HoenhiemControl;

public class HoenheimDefinition extends RobotDefinitionBase {

	public HoenheimDefinition(Properties nproperties) {
		super(nproperties);
		// TODO Auto-generated constructor stub
	}

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

	public Map<String, IControl> loadControlObjects() {
		Global.controllers=new HoenhiemControl(); //THis shouldn't be commented!
		Map<String, IControl> toReturn = super.loadControlObjects();
		new EnableCompressor(new Compressor());
		TalonMotor frontLeft = new TalonMotor(3, false);
		TalonMotor frontRight = new TalonMotor(1, true);
		TalonMotor backLeft = new TalonMotor(4, false);
		TalonMotor backRight = new TalonMotor(2, true);
		IDrive drive = new ArcadeDrive(frontLeft, frontRight, backLeft, backRight, 1./23);

		
		
		toReturn.put(DRIVENAME, drive);

		TalonMotor intakeMotor = new TalonMotor(9, false);
		DigitalInput intakeLimitSwitch = new DigitalInput(2);
		SolenoidController.getInstance().registerSolenoid("intakeSolenoid", new Solenoid(0));
		SolenoidController.getInstance().registerSolenoid("intakeSolenoid2", new Solenoid(1));
		Intake intake = new Intake(intakeMotor, intakeLimitSwitch);

		ArrayList<IMotor> shooterMotors = new ArrayList<IMotor>();
		shooterMotors.add(new TalonMotor(7, false)); // leftShooterMotor
		shooterMotors.add(new TalonMotor(5, true)); // rightShooterMotor
		ChainMotor shooterChain = new ChainMotor(shooterMotors);
		IMotorPIDOutput shooterChainControl = new IMotorPIDOutput(shooterChain);

		Encoder shooterEncoder = new Encoder(3, 4);
		XboxController controller = XboxController.getXboxController();
		DigitalInput shooterUpperLimit = new DigitalInput(1);
			// upper shooter limit switch: DIO 1
		DigitalInput shooterLowerLimit = new DigitalInput(0);
			// lower shooter limit switch: DIO 0

		Shooter shooter = new Shooter(shooterChainControl, shooterEncoder, intake, controller, shooterUpperLimit,
				shooterLowerLimit);

		return toReturn;

	}

}
