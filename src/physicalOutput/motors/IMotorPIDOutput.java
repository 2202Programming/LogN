package physicalOutput.motors;

import edu.wpi.first.wpilibj.PIDOutput;

public class IMotorPIDOutput implements PIDOutput {

	public IMotor motor;
	private boolean overide;
	private double overrideValue;
	
	public IMotorPIDOutput(IMotor nmotor) {
		motor = nmotor;
		overide = true;
		overrideValue = 0;
	}
	
	public void overideEnable(double overrideOutput){
		overide = true;
		overrideValue = overrideOutput;
	}
	
	public void overideDisable(){
		overide = false;
	}
	
	@Override
	public void pidWrite(double output) {
		if(overide)
			motor.set(overrideValue);
		else
			motor.set(output);
	}
}
 