package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import auto.*;
import tim.*;
import drive.*;
import physicalOutput.*;
import robot.Global;
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
		
		CommandListMaker CLM = new CommandListMaker();
		CommandRunner CR = new CommandRunner(CLM.makeList1(),"TIM");
		
		Global.sensors = TimSensorController.getInstance();

		temp.put("FL", FL);
		temp.put("FR", FR);
		temp.put("BL", BL);
		temp.put("BR", BR);

		temp.put("AD", AD);
		
		temp.put("CR", CR);

		return temp;
	}

}
