package piper;

import auto.CommandList;
import auto.CommandListRunner;
import auto.commands.ContinuousPegVisionCommand;
import auto.commands.DriveCommand;
import auto.stopConditions.TimerStopCondition;
import comms.SmartWriter;
import drive.DriveControl;
import drive.IDrive;
import robot.Global;
import robot.IControl;
import robotDefinitions.BabbageControl;

public class CommandListGear extends IControl{
	private CommandList commands;
	private CommandListRunner runner;
	private BabbageControl controller;
	public boolean running=false;
	
	public CommandListGear() {
		commands = new CommandList();
		//commands.addCommand(new DrivingPegVisionCommand(0.1, 0.3f));
		//commands.addCommand(new DrivingPegVisionCommand(1, .2f));
		//commands.addCommand(new RunPegVisionCommand(.6));
		//commands.addCommand(new RunPegVisionCommand(1));
		commands.addCommand(new ContinuousPegVisionCommand(100));
		commands.addCommand(new DriveCommand(new TimerStopCondition(200), 0.6));
		runner=new CommandListRunner(commands);
		controller=(BabbageControl) Global.controllers;
	}
	
	public void teleopInit() {
		running=false;
	}
	
	public void teleopPeriodic() {
		SmartWriter.putS("Drive type", ((IDrive)(Global.controlObjects.get("DRIVE"))).getDriveControl().toString());
		if (controller.startPegVision()) {
			System.out.println("Starting vision...");
			runner.init();
			((IDrive)Global.controlObjects.get("DRIVE")).setDriveControl(DriveControl.EXTERNAL_CONTROL);
			running=true;
		}
		if (running&&(controller.cancelPegVision())){
			running=false;
			((IDrive)Global.controlObjects.get("DRIVE")).setDriveControl(DriveControl.DRIVE_CONTROLLED);
			runner.stop();
		}
		if (running) {
			running=!runner.runList();
		}
	}
	
}
