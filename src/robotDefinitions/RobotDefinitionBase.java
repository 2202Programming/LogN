package robotDefinitions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

import comms.DebugMode;
import comms.SmartWriter;
import robot.IControl;

/**
 * The class that should be overridden by robot definitions
 *
 */
public abstract class RobotDefinitionBase implements IRobotDefinition {

	protected static Map<String, String> _properties;
	private static String _name;

	public static String getName(){
		return _name;
	}
	
	/**
	 * Default Constructor, uses abstract methods in order to define properties
	 */
	public RobotDefinitionBase() {
		loadManualDefinitions();
	}

	/**
	 * Override this method to load the definition name<br>
	 * <br>
	 * Called when this object is constructed
	 * 
	 * @return String value, name
	 */
	protected static void loadDefinitionName(){
		_name = _properties.get("NAME");
		SmartWriter.putS("RobotName", _name, DebugMode.COMPETITION);
	}

	/**
	 * Override this method in order to load a manual set of properties into the
	 * dictionary / map should only need to be called once in a lifetime.<br>
	 * <br>
	 * Called when this object is constructed
	 */
	protected abstract void loadManualDefinitions();

	/* (non-Javadoc)
	 * @see robotDefinitions.IRobotDefinition#loadControlObjects()
	 */
	@Override
	public Map<String, IControl> loadControlObjects() {
		Map<String, IControl> temp=new HashMap<>();

		return temp;
	}

	/**
	 * Returns the corresponding value from the properties map, in the form of a
	 * String
	 * 
	 * @param key
	 *            the value to get from the property bag,
	 * @return The corresponding value
	 */
	protected String getValue(String key) {
		return _properties.get(key);
	}

	/**
	 * Returns the corresponding value from the properties map, in the form of a
	 * int
	 * 
	 * @param key
	 *            the value to get from the property bag,
	 * @return The corresponding value
	 */
	protected int getInt(String key) {
		return Integer.parseInt(getValue(key));
	}

	/**
	 * Returns the corresponding value from the properties map, in the form of a
	 * double
	 * 
	 * @param key
	 *            the value to get from the property bag,
	 * @return The corresponding value
	 */
	protected double getDouble(String key) {
		return Double.parseDouble(getValue(key));
	}

	/**
	 * Returns the corresponding value from the properties map, in the form of a
	 * boolean
	 * 
	 * @param key
	 *            the value to get from the property bag,
	 * @return The corresponding value
	 */
	protected boolean getBool(String key) {
		String value=getValue(key).toLowerCase();
		if (value.equals("1"))
			return true;
		else if (value.equals("true"))
			return true;
		else if (value.equals("set"))
			return true;
		else if (value.equals("on"))
			return true;
		return false;
	}

	public static void loadPropertyBag() {
		if (FileLoader.fileExists("\\properties.txt")) {
			SmartWriter.putS("ErrorThisIsReallyBadGoRedeploy", "NoError", DebugMode.NOTHING);
			String[] props = FileLoader.readFile("\\properties.txt");
			for(String property: props){
				String[] split = property.split(",");
				_properties.put(split[0], split[1]);
			}
			loadDefinitionName();
		}
		else {
			SmartWriter.putS("ErrorThisIsReallyBadGoRedeploy", "NoPropertiesFileFixThisNowThisIsSuperImportant", DebugMode.NOTHING);
			//throw new StackOverflowError();
		}
	}
}
