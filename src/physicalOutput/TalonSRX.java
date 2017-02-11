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
			part.configEncoderCodesPerRev(1024);
			part.setProfile(0);
			part.setF(0.008562);
			part.setP(0.02046);
			part.setI(0);
			part.setD(0);
			
			part.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			part.reverseSensor(false);
			part.changeControlMode(TalonControlMode.Speed);
		}

		this.hasEncoder = hasEncoder;
	}

	@Override
	protected void setMotor(double x) {
		part.set(x);
	}
}
