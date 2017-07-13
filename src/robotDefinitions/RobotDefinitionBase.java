package robotDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import robot.IControl;

/**
 * The class that should be overridden by robot definitions
 *
 */
public abstract class RobotDefinitionBase implements IRobotDefinition {

	protected Properties robotProperties;
	private boolean _useXMLBag;
	public static final String DRIVENAME = "DRIVE";

	/**
	 * Default Constructor, uses abstract methods in order to define properties
	 */
	public RobotDefinitionBase(Properties nproperties) {
		_useXMLBag=useXML();
		if (_useXMLBag && nproperties != null) {
			// TODO Implement IProfile property system
			robotProperties = nproperties;
		}
		else {
			loadManualDefinitions();
		}
	}

	/**
	 * Override this method to determine whether or not to use an XML file to
	 * load properties()
	 * 
	 * @return Boolean Value
	 */
	protected abstract boolean useXML();

	/**
	 * Override this method to load the definition name<br>
	 * <br>
	 * Called when this object is constructed
	 * 
	 * @return String value, name
	 */
	protected abstract String loadDefinitionName();

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
		return robotProperties.getProperty(key);
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
}
