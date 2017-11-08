package physicalOutput.motors;

import java.util.List;

public class ChainMotor extends IMotor{
	private IMotor[] motors;
	
	public ChainMotor(List<IMotor> motorsIn){
		motors=new IMotor[motorsIn.size()];
		//n efficiency for linked list
		int counter=0;
		for (IMotor m:motorsIn) {
			motors[counter]=m;
			counter++;
		}
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
