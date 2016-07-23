package robot;

import java.util.Map;
import java.util.Map.Entry;

import comms.DebugMode;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.IterativeRobot;
import robotDefinitions.IDefinition;
import robotDefinitions.Tim;

/**
 *The main robot class that calls the IControl methods of each IControl object associated with the robot
 */
public class Robot extends IterativeRobot {

	private Map<String, IControl> controlObjects;
	IDefinition robotDefinition;

	public void robotInit() {

		// String to say which robot we are using could later be made into a XML property getter
		String name="TIM";

		// Switch to decide which robot definition to use
		switch (name) {
		case "TIM":
			robotDefinition=new Tim();
			break;
		default:
			break;
		}

		// Load all the properties in the currently selected definition
		controlObjects=robotDefinition.loadControlObjects();
	}

	public void autonomousInit() {
		SmartWriter.putS("Robot State", "Autonomous Init", DebugMode.COMPETITION);

		for (Entry<String, IControl> entry : controlObjects.entrySet()) {
			entry.getValue().autonomousInit();
		}
	}

	public void autonomousPeriodic() {
		SmartWriter.putS("Robot State", "Autonomous Periodic", DebugMode.COMPETITION);

		for (Entry<String, IControl> entry : controlObjects.entrySet()) {
			entry.getValue().autonomousInit();
		}
	}

	public void teleopInit() {
		SmartWriter.putS("Robot State", "Teleop Init", DebugMode.COMPETITION);

		for (Entry<String, IControl> entry : controlObjects.entrySet()) {
			entry.getValue().autonomousInit();
		}
	}

	public void teleopPeriodic() {
		SmartWriter.putS("Robot State", "Teleop Periodic", DebugMode.COMPETITION);

		for (Entry<String, IControl> entry : controlObjects.entrySet()) {
			entry.getValue().autonomousInit();
		}
	}

	public void disabledInit() {
		SmartWriter.putS("Robot State", "Disabled Init", DebugMode.COMPETITION);

		for (Entry<String, IControl> entry : controlObjects.entrySet()) {
			entry.getValue().autonomousInit();
		}
	}

	public void disabledPeriodic() {
		SmartWriter.putS("Robot State", "Disabled Periodic", DebugMode.COMPETITION);

		for (Entry<String, IControl> entry : controlObjects.entrySet()) {
			entry.getValue().autonomousInit();
		}
	}
}
