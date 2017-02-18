package LED;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import robot.IControl;

public class LEDController extends IControl {
	private List<Relay> redLEDs = new LinkedList<>();
	private List<Relay> blueLEDs = new LinkedList<>();
	private List<Relay> enabledLEDs = new LinkedList<>();
	
	private DriverStation ds = DriverStation.getInstance();
	
	public void addLED(Relay led, LEDActiveState state){
		switch(state){
		case RED:
			redLEDs.add(led);
			break;
		case BLUE:
			blueLEDs.add(led);
			break;
		case ENABLED:
			enabledLEDs.add(led);
			break;
		}
	}
	
	/**
	 * Checks to see if we are on the red alliance
	 * @return
	 */
	private boolean isRedTeam(){
		return ds.getAlliance() == Alliance.Red;
	}
	
	/**
	 * resets all LEDs 
	 */
	private void resetLEDs(){
		for(Relay x: redLEDs){
			x.setDirection(Direction.kReverse);
		}
		for(Relay x: blueLEDs){
			x.setDirection(Direction.kReverse);
		}
		for(Relay x: enabledLEDs){
			x.setDirection(Direction.kReverse);
		}
	}
	
	/**
	 * Activates LEDs based on what team we are on
	 */
	private void activateLEDs(){
		resetLEDs();
		for(Relay x: enabledLEDs){
			x.setDirection(Direction.kForward);
		}
		if(isRedTeam()){
			for(Relay x: redLEDs){
				x.setDirection(Direction.kForward);
			}
		}else{
			for(Relay x: blueLEDs){
				x.setDirection(Direction.kForward);
			}
		}
	}
	
	public void teleopInit(){
		activateLEDs();
	}
	
	public void autonomousInit(){
		activateLEDs();
	}
	
	public void disabledInit(){
		resetLEDs();
	}
}
