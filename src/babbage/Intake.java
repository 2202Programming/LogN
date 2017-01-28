package babbage; 

 
 import comms.XboxController; 
 import physicalOutput.IMotor; 
 import physicalOutput.SparkMotor; 
 import robot.IControl; 
 
 
 public class Intake extends IControl { 
 	private IMotor[] intakeMotors; 
 	private XboxController controller; 
 	private double speed = 0; 
 	private boolean isZero = true; 
 
 
 	// 775 motors 
 	// 2 motors ports 4 and 5 
 	public Intake() { 
 		intakeMotors = new SparkMotor[2]; 
 		intakeMotors[0] = new SparkMotor(4, true); 
 		intakeMotors[1] = new SparkMotor(5, true); 
 	} 
 
 
 	public void robotInit() { 
 	} 
 
 
 	public void autonomousInit() { 
 		controller = XboxController.getXboxController(); 
 	} 
 
 
 	public void autonomousPeriodic() { 
 		update(); 
 	} 
 
 
 	public void teleopInit() { 
 		controller = XboxController.getXboxController(); 
 	} 
 	
 
 	public void teleopPeriodic() { 
 		update(); 
 	} 
 
 
 	private void update() { 
 		if (controller.getAPressed())
 		{
 			if (speed == 0)
 				speed = .5;
 			else 
 				speed += .1;
 		}
 		else if (controller.getBPressed())
 		{
 			if (speed == .5)
 				speed = 0;
 			else 
 				speed -= .1;
 		}
 		for (IMotor motor : intakeMotors) { 
 			motor.setSpeed(speed); 
 		} 
 	} 
 } 