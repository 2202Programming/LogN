package robotDefinitions;

import java.util.HashMap;
import java.util.Map;

import robot.IControl;

/**
 * The class that should be overridden by robot definitions
 *
 */
public abstract class RobotDefinitionBase implements IRobotDefinition {

	protected Map<String, String> _properties;
	private boolean _useXMLBag;
	private String _name;
	public static final String DRIVENAME = "DRIVE";

	/**
	 * Default Constructor, uses abstract methods in order to define properties
	 */
	public RobotDefinitionBase() {
		_name=loadDefinitionName();
		_useXMLBag=useXML();
		loadPropertyBag();
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

	private void loadPropertyBag() {
		if (_useXMLBag) {
			// TODO Implement IProfile property system
			switch (_name) {
				case "TIM":
					break;
				default:
					break;
			}
		}
		else {
			loadManualDefinitions();
		}
	}
}
