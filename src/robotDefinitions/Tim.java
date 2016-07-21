package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import auto.CommandList;
import auto.CommandRunner;
import auto.DriveCommand;
import drive.ArcadeDrive;
import drive.IDrive;
import motors.IMotor;
import motors.SparkMotor;
import robot.IControl;

/**
 * The Tim implementation of IDefinition.<br>
 * <br>
 * Comments are in IDefinition
 */
public class Tim extends IDefinition {

	@Override
	protected boolean useXML() {
		return false;
	}

	@Override
	protected String loadDefinitionName() {
		return "TIM";
	}

	@Override
	protected void loadManualDefinitions() {
		_properties=new HashMap<String, String>();

	}

	public Map<String, IControl> loadControlObjects() {
		Map<String, IControl> temp=super.loadControlObjects();

		IMotor FL=new SparkMotor(0);
		IMotor FR=new SparkMotor(1);
		IMotor BL=new SparkMotor(2);
		IMotor BR=new SparkMotor(3);

		IDrive AD=new ArcadeDrive(FL, FR, BL, BR);
		
		CommandList CL = new CommandList();
		//create Command list here somehow (see example below)
		CL.addCommand(new DriveCommand(0,0));
		
		CommandRunner CR = new CommandRunner(CL,"TIM");


		temp.put("FL", FL);
		temp.put("FR", FR);
		temp.put("BL", BL);
		temp.put("BR", BR);

		temp.put("AD", AD);
		
		temp.put("CR", CR);

		return temp;
	}

}
