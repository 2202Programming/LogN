package comms;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import robot.IControl;

public class NetworkTables extends IControl{

	NetworkTable table;
	
	public NetworkTables(TableNamesEnum tableKey) {
		table = NetworkTable.getTable(tableKey.toString());
	}
	
	public double getDouble(String key){
		return table.getNumber(key, 0);
	}
	
	public void setDouble(String key, double value){
		table.putNumber(key, value);
	}
	
	public String getString(String key){
		return table.getString(key, "");
	}
	
	public void setString(String key, String value){
		table.putString(key, value);
	}
	
	public boolean getBoolean(String key){
		return table.getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue){
		return table.getBoolean(key, defaultValue);
	}
	
	public void setBoolean(String key, boolean value){
		table.putBoolean(key, value);
	}
}
