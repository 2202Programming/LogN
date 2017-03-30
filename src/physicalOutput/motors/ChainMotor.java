package physicalOutput.motors;

import java.util.List;

public class ChainMotor extends IMotor{
	IMotor[] motors;
	
	public ChainMotor(List<IMotor> motorsIn){
		motors = (IMotor[]) motorsIn.toArray();
	}
	
	public ChainMotor(IMotor[] motorsIn){
		motors = motorsIn;
	}
	
	public ChainMotor(IMotor motor1, IMotor motor2){
		motors = new IMotor[] {motor1, motor2};
	}
	
	@Override
	protected void setMotor(double speed) {
		for(IMotor x: motors){
			x.set(speed);
		}
	}

}
