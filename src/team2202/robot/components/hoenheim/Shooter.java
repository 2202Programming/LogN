package team2202.robot.components.hoenheim;

import comms.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import physicalOutput.motors.IMotor;
import robot.IControl;

public class Shooter extends IControl {
	
	final double Kp = 0.009;
	final double Ki = 0.0003; 
	final double Kd = 0.006;
	
	private IMotor shootMotorChain;
	private Encoder shootEncoder;
	private Intake intake;
	private XboxController xboxController;
	private DigitalInput upperLimit;
	private DigitalInput lowerLimit;
	
	private PIDController pidController;
	private PIDOutput pidOutput;

	private boolean readyShot;
	private boolean lobShot;
	private boolean heavyShot;
	private boolean normalShot;
	int twoStageSetupPosition;
	double twoStagePidSetup;
	double twoStageEndPosition;
	double twoStagePidFire;
	int maxEncoderValue;

	private ShooterState state;
	private boolean canFire;

	public enum ShooterState {
		STANDBY, RESET, READY_SHOT, SHOT_READY, STAGE_ONE_SHOT, STAGE_TWO_SHOT, FIRE
	}
	
	private class PIDOutput implements edu.wpi.first.wpilibj.PIDOutput {

		private double output;
		
		public double getOutput(){
			return output;
		}
		@Override
		public void pidWrite(double _output) {
			// TODO Auto-generated method stub
			output = _output;
		}
		
	}

	public Shooter(IMotor nshootMotorChain, Encoder nshootEncoder, Intake nintake, XboxController nxboxController,
			DigitalInput nupperLimit, DigitalInput nlowerLimit) {
		shootMotorChain = nshootMotorChain;
		shootEncoder    = nshootEncoder;
		intake          = nintake;
		xboxController  = nxboxController;
		upperLimit      = nupperLimit;
		lowerLimit      = nlowerLimit;
		
		pidOutput       = new PIDOutput();
		pidController   = new PIDController(Kp, Ki, Kd, shootEncoder, pidOutput);
	}

	private boolean canShoot() {
		// TODO get state from intake
		return true;
	}

	public ShooterState getState() {
		return state;
	}

	public void setState(ShooterState state) {
		this.state = state;
	}

	public void getInput() {
		readyShot  = xboxController.getRightTriggerHeld();
		lobShot    = xboxController.getYPressed();
		heavyShot  = xboxController.getXPressed();
		normalShot = xboxController.getBackPressed();
	}

	public void TeleopInit() {
		state = ShooterState.STANDBY;
		shootEncoder.reset();
		shootMotorChain.set(0);
	}
	


	public void TeleopPeriodic() {
		getInput();

		switch (state) {
		case RESET:
			intake.setShooting(false);
			break;
		case STANDBY:
			if (readyShot)
				state = ShooterState.READY_SHOT;
			break;
		case READY_SHOT:
			
			if (!readyShot) {
				state = ShooterState.RESET;
			}
			
			if (intake.setShooting(true))
				state = ShooterState.SHOT_READY;
			
			break;
		case SHOT_READY:
			if (lobShot) {
				twoStageSetupPosition = 5;
				twoStagePidSetup = -0.08;
				twoStageEndPosition = 250;
				twoStagePidFire = 0.95;
				state = ShooterState.STAGE_TWO_SHOT;
				maxEncoderValue = 0;
				//pIDControlOutput->PIDOverideEnable(twoStagePidFire);
			} else if (heavyShot) {
				twoStageSetupPosition = 5;
				twoStagePidSetup = -0.08;
				twoStageEndPosition = 250;
				twoStagePidFire = 0.95;
				state = ShooterState.STAGE_TWO_SHOT;
				maxEncoderValue = 0;
				//pIDControlOutput->PIDOverideEnable(twoStagePidFire);
			} else if (normalShot) {
				state = ShooterState.FIRE;
				maxEncoderValue = 0;
				//pIDControlOutput->PIDOverideEnable(MANUALPIDFIRE);
			}
			break;
		case FIRE:
			boolean isUpperLimit = upperLimit.get();
			break;
		default:
			break;
		}
	}
}
