package babbage;

import auto.CommandList;
import auto.commands.DriveCommand;
import auto.commands.RunPegVisionCommand;
import auto.commands.TurnCommand;
import auto.stopConditions.AngleStopCondition;
import auto.stopConditions.DistanceStopCondition;
import comms.DebugMode;
import comms.SmartWriter;

//TODO all distances, speeds and angles are not final

public class BabbageAutoLists {
	private final static double speed = 0.7;
	private final static int distanceFromWall = 39;
	
	/** Tells the robot to move forward. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Red Center position
	 */
	public static CommandList centerRed(){
		CommandList CL = new CommandList();
		SmartWriter.putS("AutoList", "centerRed", DebugMode.COMPETITION);
		CL.addCommand(new DriveCommand(new DistanceStopCondition(null, distanceFromWall), speed));
		CL.addCommand(new RunPegVisionCommand());
		return CL;
	}
	
	/** Tells the robot to move forward and then turns 45 degrees to the left. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Red Boiler position
	 */
	public static CommandList boilerRed(){
		CommandList CL = new CommandList();
		SmartWriter.putS("AutoList", "Boiler Red", DebugMode.COMPETITION);
		CL.addCommand(new DriveCommand(new DistanceStopCondition(null, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(-45,1,0.3)));
		CL.addCommand(new RunPegVisionCommand());
		return CL;
	}

	/** Tells the robot to move forward and then turns 45 degrees to the right. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Red Non-Boiler position
	 */
	public static CommandList notBoilerRed(){
		CommandList CL = new CommandList();
		SmartWriter.putS("AutoList", "Not Boiler Red", DebugMode.COMPETITION);
		CL.addCommand(new DriveCommand(new DistanceStopCondition(null, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(45,1,0.3)));
		CL.addCommand(new RunPegVisionCommand());
		return CL;
	}
	
	/** Tells the robot to move forward. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Blue Center position
	 */
	public static CommandList centerBlue(){
		CommandList CL = new CommandList();
		SmartWriter.putS("AutoList", "Center Blue", DebugMode.COMPETITION);
		CL.addCommand(new DriveCommand(new DistanceStopCondition(null, distanceFromWall)));
		CL.addCommand(new RunPegVisionCommand());
		return CL;
	}
	
	/** Tells the robot to move forward and then turns 45 degrees to the right. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Blue Boiler position
	 */
	public static CommandList boilerBlue(){
		CommandList CL = new CommandList();
		SmartWriter.putS("AutoList", "Boilerblue", DebugMode.COMPETITION);
		CL.addCommand(new DriveCommand(new DistanceStopCondition(null, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(45,1,0.3)));
		CL.addCommand(new RunPegVisionCommand());
		return CL;
	}
	
	
	/** Tells the robot to move forward and then turns 45 degrees to the left. Then, it runs
	 * the PegVision
	 * 
	 * @return returns Commandlist for the Blue Non-Boiler position
	 */
	public static CommandList notBoilerBlue(){
		CommandList CL = new CommandList();
		SmartWriter.putS("AutoList", "Not Boiler Blue", DebugMode.COMPETITION);
		CL.addCommand(new DriveCommand(new DistanceStopCondition(null, distanceFromWall), speed));
		CL.addCommand(new TurnCommand(new AngleStopCondition(-45,1,0.3)));
		CL.addCommand(new RunPegVisionCommand());
		return CL;
	}
}
