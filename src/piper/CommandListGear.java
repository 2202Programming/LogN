package piper;

import auto.CommandList;
import auto.CommandListRunner;
import auto.commands.DrivingPegVisionCommand;
import auto.commands.RunPegVisionCommand;
import comms.SmartWriter;
import comms.XboxController;
import drive.DriveControl;
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
		commands.addCommand(new DrivingPegVisionCommand(1));
		//commands.addCommand(new RunPegVisionCommand(.5));
		//commands.addCommand(new RunPegVisionCommand(.8));
		runner=new CommandListRunner(commands);
		controller=XboxController.getXboxController();
	}
	
	public void teleopInit() {
		running=false;
	}
	
	public void teleopPeriodic() {
		SmartWriter.putS("Drive type", ((IDrive)(Global.controlObjects.get("DRIVE"))).getDriveControl().toString());
		if (controller.getYPressed()) {
			runner.init();
			running=true;
		}
		if (running&&(controller.getStartPressed()||controller.getStartHeld())) {
			running=false;
			((IDrive)Global.controlObjects.get("DRIVE")).setDriveControl(DriveControl.DRIVE_CONTROLLED);
			runner.stop();
		}
		if (running) {
			running=!runner.runList();
		}
	}
	
}
