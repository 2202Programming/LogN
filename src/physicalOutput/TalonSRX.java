package physicalOutput;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class TalonSRX extends IMotor {

	CANTalon part;
	private boolean hasEncoder;

	public TalonSRX(int x, boolean reverse) {
		super(reverse);
		part = new CANTalon(x);
		part.changeControlMode(TalonControlMode.PercentVbus);
		hasEncoder = false;
	}

	public TalonSRX(int x, boolean reverse, boolean hasEncoder) {
		this(x, reverse);
		if (hasEncoder) {
			part.configEncoderCodesPerRev(4096);
			part.setProfile(0);
			part.setF(0.013); /// 0.008562
			part.setP(0.018); // 102/4000 .1*1023/4000Uerror = 0.0255
			part.setI(0.00002); // set P/100 to start
			part.setD(0.0000);
			part.setAllowableClosedLoopErr(0);

			part.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			part.reverseSensor(false);
			part.changeControlMode(TalonControlMode.Speed);

			// limit the error wind up, greater than IZone, zeros the integrator.
			//
			// err= 500RPM (50r/min)*(4096U/rev)*(1/60 min/s)*(1/10 s/100ms)
			// = 3410 U/100ms (U is pulses or native units)
			//
			part.setIZone(0); // should be in native-units (pulses/100ms)
			// Izone seems to break the CL control by zeroing the iAcc when the err gets to zero
			// this feels like a bug in the SRX. - Derek
			part.setEncPosition(0);
			part.ClearIaccum();
		}

		this.hasEncoder = hasEncoder;
	}

	public void teleopInit() {
		super.teleopInit();
		if (hasEncoder) {
			part.setEncPosition(0);
			part.ClearIaccum();
			part.clearStickyFaults();
			part.setAllowableClosedLoopErr(20);
		}
	}

	@Override
	protected void setMotor(double x) {
		part.set((x + 1) / 2f);
	}

	public double getSpeed() {
		return part.getSpeed();
	}
}
