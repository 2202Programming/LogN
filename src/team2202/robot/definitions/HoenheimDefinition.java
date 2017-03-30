package team2202.robot.definitions;

import java.util.Map;

import drive.ArcadeDrive;
import drive.IDrive;
import physicalOutput.TalonMotor;
import robot.IControl;
import robotDefinitions.RobotDefinitionBase;

public class HoenheimDefinition extends RobotDefinitionBase {

	@Override
	protected boolean useXML() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String loadDefinitionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void loadManualDefinitions() {
		// TODO Auto-generated method stub
		
	}
	
	public Map<String,IControl> loadControlObjects(){
		Map<String,IControl> toReturn = super.loadControlObjects();
		IDrive drive = new ArcadeDrive(
				new TalonMotor(getInt("DM_FRONTLEFT"), true) ,
				new TalonMotor(getInt("DM_FRONTRIGHT"), true),
				new TalonMotor(getInt("DM_BACKLEFT"), true), 
				new TalonMotor(getInt("DM_BACKRIGHT"), true));
		
		toReturn.put(DRIVENAME, drive);
		
		return toReturn;
	}

}
