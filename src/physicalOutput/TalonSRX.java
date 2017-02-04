package physicalOutput;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class TalonSRX  extends IMotor{

	CANTalon part;
	
	public TalonSRX(int x,boolean reverse) {
    	super(reverse);
    	part = new CANTalon(x);
    	part.changeControlMode(TalonControlMode.Speed);
    }
	
	public TalonSRX(int x,boolean reverse, boolean hasEncoder){
		this(x,reverse);
		part.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		part.reverseSensor(false);
	}
	
	@Override
	protected void setMotor(double x) {
		// TODO Auto-generated method stub
		
	}

}
