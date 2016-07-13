/***
 * 
 */
package robotDefinitions;

import java.util.HashMap;
import java.util.Map;
import robot.IControl;

/***
 * @author lazar
 */
public abstract class IDefinition {

	protected Map<String, String> _properties;
	private boolean _useXMLBag;
	private String _name;

	/**
	Defulkt Constructor, uses abstract methods in order to define properties
	*/
	public IDefinition() {
		_name=loadDefinitionName();
		_useXMLBag=useXML();
		loadPropertyBag();
	}

	/**
	Overide this method to determine weither or not to use a xml file to load properties()

	@return Boolean Value
	*/
	protected abstract boolean useXML();

	/**
	Overide this method to load the definition name

	@return String value, name
	*/
	protected abstract String loadDefinitionName();

	/**
	Overide this method in order to load a manuel set of properties into the dictionary / map
		should only need to be called once in a lifetime.
	*/
	protected abstract void loadManualDefinitions();

	/**
	Loads the control objects defined from the values in the properties, this should only need, to be called
	once in a lifetime, duplicate objects may not function as intended.

	@return Control Object List for robot class's main cycle
	*/
	public Map<String, IControl> loadControlObjects() {
		Map<String, IControl> temp=new HashMap<>();

		return temp;
	}

	/** 
		Ruturns the coresponding value from the properties map, in the form of a String
		@param key the value to get from the property bag,
		@return The coresponding value
	 */
	protected String getValue(String key) {
		return _properties.get(key);
	}

	/** 
		Ruturns the coresponding value from the properties map, in the form of a int
		@param key the value to get from the property bag,
		@return The coresponding value
	 */
	protected int getInt(String key) {
		return Integer.parseInt(getValue(key));
	}

	/** 
		Ruturns the coresponding value from the properties map, in the form of a double
		@param key the value to get from the property bag,
		@return The coresponding value
	 */
	protected double getDouble(String key) {
		return Double.parseDouble(getValue(key));
	}

	/** 
		Ruturns the coresponding value from the properties map, in the form of a boolean
		@param key the value to get from the property bag,
		@return The coresponding value
	 */
	protected boolean getBool(String key) {
		String value=getValue(key).toLowerCase();
		if (value.equals("1")) return true;
		else if (value.equals("true")) return true;
		else if (value.equals("set")) return true;
		else if (value.equals("on")) return true;
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
