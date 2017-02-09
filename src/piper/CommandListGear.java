package piper;

import auto.CommandList;
import auto.CommandListRunner;
import auto.commands.RunPegVisionCommand;
import comms.SmartWriter;
import comms.XboxController;
import drive.IDrive;
import robot.Global;
import robot.IControl;

public class CommandListGear extends IControl{
	private CommandList commands;
	private CommandListRunner runner;
	private XboxController controller;
	public boolean running=false;
	
	public CommandListGear() {
		commands = new CommandList();
		commands.addCommand(new RunPegVisionCommand());
		runner=new CommandListRunner(commands);
		controller=XboxController.getXboxController();
	}
	
	public void teleopPeriodic() {
		SmartWriter.putS("Drive type", ((IDrive)(Global.controlObjects.get("DRIVE"))).getDriveControl().toString());
		if (controller.getYPressed()) {
			runner.init();
			running=true;
		}
		if (running) {
			running=!runner.runList();
		}
	}
	
}
