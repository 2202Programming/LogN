package robotDefinitions;

import java.util.HashMap;
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
		_properties = new HashMap<String, String>();
		
	}
	
	protected Map<String, IControl> loadControlObjects()
	{
		Map<String, IControl> temp = new HashMap<>();
		
		IMotor FL = new SparkMotor(0);
		IMotor FR = new SparkMotor(1);
		IMotor BL = new SparkMotor(2);
		IMotor BR = new SparkMotor(3);
		
		
		return temp;
	}

}
