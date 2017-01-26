package comms;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import robot.IControl;

public class NetworkTables extends IControl{
	NetworkTable table;
	public NetworkTables(String tableKey) {
		table = NetworkTable.getTable(tableKey);
	}
	
	public double getDouble(String key){
		return table.getNumber(key, -1);
	}
	
	public void setDouble(String key, double value){
		table.putNumber(key, value);
	}
}
