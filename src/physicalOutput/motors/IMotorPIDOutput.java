package physicalOutput.motors;

import edu.wpi.first.wpilibj.PIDOutput;

public class IMotorPIDOutput implements PIDOutput {

	IMotor motor;
	private boolean overide;
	
	public IMotorPIDOutput(IMotor nmotor) {
		motor = nmotor;
		overide = true;
	}
	
	public void overideEnable(){
		overide = true;
	}
	
	public void overideDisable(){
		overide = false;
	}
	
	@Override
	public void pidWrite(double output) {
		if(overide)
			motor.set(0);
		else
			motor.set(output);
	}
}
 