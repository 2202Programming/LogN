package team2202.robot.components.babbage;

import java.util.ArrayList;
import java.util.List;

import auto.CommandList;
import auto.commands.DriveCommand;
import auto.commands.ShootCommand;
import auto.commands.TurnCommand;
import auto.commands.WaitCommand;
import auto.stopConditions.AngleStopCondition;
import auto.stopConditions.DistanceStopCondition;
import auto.stopConditions.TimerStopCondition;
import edu.wpi.first.wpilibj.Encoder;
import input.SensorController;
import team2202.robot.autoCommands.ContinuousPegVisionCommand;

//TODO all distances, speeds and angles are not final

public class BabbageAutoLists {
	private final static double speed = 0.7;
	private final static int distanceFromWall = 60;
	private final static int turnAngle = 60;
	private final static double turnTolerance = 2;
	private final static double toleranceTime = 0.2;
	private static SensorController sensors = SensorController.getInstance();
	private static List<Encoder> encoders = new ArrayList<Encoder>();
	
	
	public static CommandList shootingRed() {
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		CommandList cl=new CommandList();
		cl.addCommand(new DriveCommand(new DistanceStopCondition(encoders, 64), 0.6));
		cl.addCommand(new TurnCommand(-90, 10, 0.3));
		cl.addCommand(new DriveCommand(new TimerStopCondition(1000), -0.6));
		cl.addCommand(new ShootCommand());
		return cl;
	}
	
	public static CommandList shootingBlue() {
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		CommandList cl=new CommandList();
		cl.addCommand(new DriveCommand(new DistanceStopCondition(encoders, 64), 0.6));
		cl.addCommand(new TurnCommand(-90, 10, 0.3));
		cl.addCommand(new DriveCommand(new TimerStopCondition(1000), 0.6));
		cl.addCommand(new ShootCommand());
		return cl;
	}
	
	/** Tells the robot to move forward. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Red Center position
	 */
	public static CommandList centerRed(){
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		CommandList CL = new CommandList();
		CL.addCommand(new WaitCommand(new TimerStopCondition(500)));
		CL.addCommand(new ContinuousPegVisionCommand(1, 4000));
		CL.addCommand(new DriveCommand(new TimerStopCondition(400), 1));
		//CL.addCommand(new ShootCommand());
		return CL;
	}
	
	/** Tells the robot to move forward and then turns 45 degrees to the left. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Red Boiler position
	 */
	public static CommandList boilerRed(){
		
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		
		//TODO add timer built into this 
		CommandList CL = new CommandList();
		CL.addCommand(new DriveCommand(new DistanceStopCondition(encoders, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(-turnAngle,turnTolerance,toleranceTime)));
		CL.addCommand(new ContinuousPegVisionCommand(1, 4000));
		CL.addCommand(new DriveCommand(new TimerStopCondition(400), 1));
		return CL;
	}

	/** Tells the robot to move forward and then turns 45 degrees to the right. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Red Non-Boiler position
	 */
	public static CommandList notBoilerRed(){
		
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		
		CommandList CL = new CommandList();
		CL.addCommand(new DriveCommand(new DistanceStopCondition(encoders, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(turnAngle,turnTolerance,toleranceTime)));
		CL.addCommand(new ContinuousPegVisionCommand(1, 4000));
		CL.addCommand(new DriveCommand(new TimerStopCondition(400), 1));
		return CL;
	}
	
	/** Tells the robot to move forward. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Blue Center position
	 */
	public static CommandList centerBlue(){
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		CommandList CL = new CommandList();
		CL.addCommand(new WaitCommand(new TimerStopCondition(500)));
		CL.addCommand(new ContinuousPegVisionCommand(1, 4000));
		CL.addCommand(new DriveCommand(new TimerStopCondition(400), 1));
		return CL;
	}
	
	/** Tells the robot to move forward and then turns 45 degrees to the right. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Blue Boiler position
	 */
	public static CommandList boilerBlue(){
		
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		
		CommandList CL = new CommandList();
		CL.addCommand(new DriveCommand(new DistanceStopCondition(encoders, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(turnAngle,turnTolerance,toleranceTime)));
		CL.addCommand(new ContinuousPegVisionCommand(1, 4000));
		CL.addCommand(new DriveCommand(new TimerStopCondition(400), 1));
		return CL;
	}
	
	
	/** Tells the robot to move forward and then turns 45 degrees to the left. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Blue Non-Boiler position
	 */
	public static CommandList notBoilerBlue(){
		
		encoders.add((Encoder) sensors.getSensor("ENCODER0"));
		
		CommandList CL = new CommandList();
		CL.addCommand(new DriveCommand(new DistanceStopCondition(encoders, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(-turnAngle,turnTolerance,toleranceTime)));
		CL.addCommand(new ContinuousPegVisionCommand(1, 4000));
		CL.addCommand(new DriveCommand(new TimerStopCondition(400), 1));
		return CL;
	}
}
