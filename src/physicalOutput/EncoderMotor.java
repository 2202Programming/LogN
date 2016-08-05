package physicalOutput;

import edu.wpi.first.wpilibj.Encoder;

//more leinad's stuff

/**
 * 
 */
public class EncoderMotor extends IMotor {

	/**
	 * Default constructor
	 */
	public EncoderMotor() {
	}

	/**
	 * 
	 */
	private IMotor motor;

	/**
	 * 
	 */
	private Encoder encoder;

	/**
	 * 
	 */
	private boolean enabled;

	/**
	 * Constructor for encoder controlled motor
	 * 
	 * @param motorIn
	 *            motor to be added
	 * @param encoderIn
	 *            encoder to be added
	 */
	public EncoderMotor(IMotor motorIn, Encoder encoderIn) {
		motor=motorIn;
		encoder=encoderIn;
	}

	/**
	 * Sets the speed of the motor <br>
	 * Preconditions: speed is between -1.0 and 1.0<br>
	 * Postconditions: sets the speed
	 * 
	 * @param speed
	 */
	public void setMotor(double speed) {
		if (enabled) motor.setSpeed(speed);
	}
}