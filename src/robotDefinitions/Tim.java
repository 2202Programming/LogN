package robotDefinitions;

import java.util.HashMap;
import motors.*;
import drive.ArcadeDrive;
import java.util.Map;

import robot.*;

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
		Map<String, IControl> temp = super.loadControlObjects();

		IMotor FL=new SparkMotor(0);
		IMotor FR=new SparkMotor(1);
		IMotor BL=new SparkMotor(2);
		IMotor BR=new SparkMotor(3);

		ArcadeDrive AD=new ArcadeDrive(FL, FR, BL, BR);

		temp.put("FL", FL);
		temp.put("FR", FR);
		temp.put("BL", BL);
		temp.put("BR", BR);

		temp.put("AD", AD);

		return temp;
	}

}
