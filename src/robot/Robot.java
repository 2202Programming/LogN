package robot;

import comms.DebugMode;
import comms.SmartWriter;
import edu.wpi.first.wpilibj.IterativeRobot;
import robotDefinitions.ControlBase;
import robotDefinitions.IRobotDefinition;
import robotDefinitions.RobotName;
import team2202.robot.definitions.Babbage;
import team2202.robot.definitions.HoenheimDefinition;
import team2202.robot.definitions.MechanumRobot;
import team2202.robot.definitions.Piper;
import team2202.robot.definitions.Tim;

/**
 * The main robot class that calls the IControl methods of each IControl object
 * associated with the robot comment here
 */
public class Robot extends IterativeRobot {

	private IRobotDefinition robotDefinition;
	public static RobotName name;

	public void robotInit() {
		SmartWriter.putS("Robot State", "Initsing", DebugMode.DEBUG);
		// String to say which robot we are using could later be made into a XML
		// property getter
		name=RobotName.PIPER; // TODO Can we get this from the robot so
										// it automatically knows what robot it
										// is?
		SmartWriter.putS("RobotName", name.toString(), DebugMode.COMPETITION);
		// Switch to decide which robot definition to use
		switch (name) {
		case TIM:
			robotDefinition=new Tim();//probably broken
			break;
		case PIPER:
			robotDefinition=new Piper();
			break;
		case MECHANUMDRIVE:
			robotDefinition=new MechanumRobot();
			break;
		case BABBAGE:
			robotDefinition=new Babbage();
			break;
		case HOENHIEM:
			robotDefinition=new HoenheimDefinition();
			break;
		default:
			break;
		}

		// Load all the properties in the currently selected definition
		Global.controlObjects=robotDefinition.loadControlObjects();
		 
	}

	public void autonomousInit() {
		SmartWriter.putS("Robot State", "Autonomous Init", DebugMode.COMPETITION);
		try {
			IControl.callAutonomousInit();
		} catch (Exception e) {
			SmartWriter.outputError(e, "Auto Init");
		}
	}

	public void autonomousPeriodic() {
		SmartWriter.putS("Robot State", "Autonomous Periodic", DebugMode.COMPETITION);
		try {
			IControl.callAutonomousPeriodic();
		} catch (Exception e) {
			SmartWriter.outputError(e, "Auto Periodic");
		}
	}

	public void teleopInit() {
		SmartWriter.putS("Robot State", "Teleop Init", DebugMode.COMPETITION);
		try {
			IControl.callTeleopInit();
		} catch (Exception e) {
			SmartWriter.outputError(e, "Teleop Init");
		}
	}

	public void teleopPeriodic() {
		SmartWriter.putS("Robot State", "Teleop Periodic", DebugMode.COMPETITION);
		try {
			IControl.callTeleopPeriodic();
		} catch (Exception e) {
			SmartWriter.putB("error", true, DebugMode.DEBUG);
			SmartWriter.outputError(e, "Teleop Periodic");
		}
	}

	public void disabledInit() {
		
		SmartWriter.putS("Robot State", "Disabled Init", DebugMode.COMPETITION);
		try {
			IControl.callDisabledInit();
		} catch (Exception e) {
			SmartWriter.outputError(e, "Disabled Init");
		}
	}

	public void disabledPeriodic() {
		
		SmartWriter.putS("Robot State", "Disabled Periodic", DebugMode.COMPETITION);
		try {
			IControl.callDisabledPeriodic();
		} catch (Exception e) {
			SmartWriter.outputError(e, "Disabled Periodic");
		}
	}
}
