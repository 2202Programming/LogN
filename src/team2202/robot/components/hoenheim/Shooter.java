package team2202.robot.components.hoenheim;

import comms.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import physicalOutput.motors.IMotorPIDOutput;
import robot.IControl;

public class Shooter extends IControl {
  final double Kp                   = 0.009;
  final double Ki                   = 0.0003;
  final double Kd                   = 0.006;
  final double READYTOFIRE          = 30;
  final double FIRE                 = 225;
  final double PIDTOLERANCE         = 5.0;
  final double RETRACTCPS           = 250;
  final double SHOOTCPS             = 900;
  final double LOADCPS              = 100.0;
  final double VARRIABLEAUTOPIDFIRE = 0.92;

  final double RIGHT         = 5000;
  final double HOME          = 5;
  final double ARMING        = 0;
  final double STOPPEDSPEED  = 0.0;
  final double FIRESPEED     = 0.7;
  final double LOADINGSPEED  = 0.4;
  final double ARMINGSPEED   = -0.1;
  final double SHOOTINGSPEED = 0.5;
  final double MANUALPIDFIRE = 1.00;
  final double AUTOPIDFIRE   = 1.00;

  private IMotorPIDOutput shootMotorChain;
  private Encoder shootEncoder;
  private Intake intake;
  private XboxController xboxController;
  private DigitalInput upperLimit;
  private DigitalInput lowerLimit;

  private PIDController pidController;
  private Timer shooterTimer;

  private boolean readyShot;
  private boolean lobShot;
  private boolean heavyShot;
  private boolean normalShot;
  private int twoStageSetupPosition;
  private double twoStagePidSetup;
  private double twoStageEndPosition;
  private double twoStagePidFire;
  private int maxEncoderValue;
  private double previousTime;

  private ShooterState state;

  public enum ShooterState {
    STANDBY, RESET, READY_SHOT, SHOT_READY, STAGE_ONE_SHOT, STAGE_TWO_SHOT, FIRE,
    RETRACTING, INIT
  }


  public Shooter(IMotorPIDOutput nshootMotorChain,
                 Encoder         nshootEncoder,
                 Intake          nintake,
                 XboxController  nxboxController,
                 DigitalInput    nupperLimit,
                 DigitalInput    nlowerLimit) {
    shootMotorChain = nshootMotorChain;
    shootEncoder    = nshootEncoder;
    intake          = nintake;
    xboxController  = nxboxController;
    upperLimit      = nupperLimit;
    lowerLimit      = nlowerLimit;

    pidController = new PIDController(Kp, Ki, Kd, shootEncoder, shootMotorChain);
    shooterTimer  = new Timer();
  }

  private void getInput() {
    readyShot  = xboxController.getRightTriggerHeld();
    lobShot    = xboxController.getYPressed();
    heavyShot  = xboxController.getXPressed();
    normalShot = xboxController.getBackPressed();
  }

  private double downRampProfile(double timeChange) {
    return timeChange * -RETRACTCPS;
  }

  private double shootRampProfile(double timeChange) {
    return timeChange * SHOOTCPS;
  }

  private void ShooterStateMachine() {
    getInput();
    boolean isLowerLimit = lowerLimit.get();

    // for ramping
    double timeChange = (shooterTimer.get() - previousTime);
    previousTime = shooterTimer.get();
    int count = shootEncoder.get();

    if (maxEncoderValue < count) {
      maxEncoderValue = count;
    }

    switch (state) {
    case INIT:
      if (isLowerLimit) {
        shootMotorChain.pidWrite(STOPPEDSPEED);

				// If the Shooter is done moving
        if (Math.abs(shootEncoder.getRate()) < STOPPEDSPEED + 0.1) {
          shootEncoder.reset();
          pidController.enable();
          pidController.setSetpoint(HOME);
          state = ShooterState.STANDBY;
        }
      } 

    case RESET:

      if (isLowerLimit && (shootEncoder.getRate() < STOPPEDSPEED + 0.1)) {
        state = ShooterState.STANDBY;
      }
      shootMotorChain.pidWrite(STOPPEDSPEED);
      intake.setShooting(false);
      break;

    case STANDBY:

      if (readyShot) state = ShooterState.READY_SHOT;
      break;

    case READY_SHOT:

      if (!readyShot) {
        state = ShooterState.RESET;
      }

      if (intake.setShooting(true)) state = ShooterState.SHOT_READY;

      break;

    case SHOT_READY:

      if (lobShot) {
        twoStageSetupPosition = 5;
        twoStagePidSetup      = -0.08;
        twoStageEndPosition   = 250;
        twoStagePidFire       = 0.95;
        state                 = ShooterState.STAGE_TWO_SHOT;
        maxEncoderValue       = 0;

        // pIDControlOutput->PIDOverideEnable(twoStagePidFire);
      } else if (heavyShot) {
        twoStageSetupPosition = 5;
        twoStagePidSetup      = -0.08;
        twoStageEndPosition   = 250;
        twoStagePidFire       = 0.95;
        state                 = ShooterState.STAGE_TWO_SHOT;
        maxEncoderValue       = 0;

        // pIDControlOutput->PIDOverideEnable(twoStagePidFire);
      } else if (normalShot) {
        state           = ShooterState.FIRE;
        maxEncoderValue = 0;

        // pIDControlOutput->PIDOverideEnable(MANUALPIDFIRE);
      }
      break;

    case FIRE:

      if (upperLimit.get() || (shootEncoder.get() >= FIRE)) {
        pidController.setSetpoint(FIRE);
        shootMotorChain.overideDisable();
        state = ShooterState.RETRACTING;
      } else {
        double countChange = shootRampProfile(timeChange);
        double newSetPoint = pidController.getSetpoint() + countChange;

        if (newSetPoint >= FIRE) newSetPoint = FIRE;
        pidController.setSetpoint(newSetPoint);
      }
      break;

    case RETRACTING:

      if (shootEncoder.get() <= HOME + 5) {
        pidController.setSetpoint(HOME);
        state = ShooterState.STANDBY;
      }
      double positionChange = downRampProfile(timeChange);
      double newSetpoint    = pidController.getSetpoint() + positionChange;

      if (newSetpoint <= HOME) {
        newSetpoint = HOME;
      }
      pidController.setSetpoint(newSetpoint);
      break;

    default:
      break;
    }
  }

  public void TeleopInit() {
    state = ShooterState.STANDBY;
    shootEncoder.reset();

    shooterTimer.stop();
    shooterTimer.reset();
    shooterTimer.start();

    previousTime = 0;
  }

  public void TeleopPeriodic() {
    ShooterStateMachine();
  }
}
