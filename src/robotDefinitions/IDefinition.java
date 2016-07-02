/**
 * 
 */
package robotDefinitions;

import java.util.Map;
import robot.IControl;

/**
 * @author lazar
 *
 */
public abstract class IDefinition {

	protected Map<String, String> _properties;
	private boolean _useXMLBag;
	private String _name;
	
	public IDefinition()
	{
		_name = loadDefinitionName();
		_useXMLBag = useXML();
		loadPropertyBag();
	}
	
	protected abstract boolean useXML();	
	protected abstract String loadDefinitionName();
	protected abstract void loadManualDefinitions();
	protected abstract Map<String, IControl> loadControlObjects();
	
	protected String getValue(String key)
	{
		return _properties.get(key);
	}
	
	protected int getInt(String key)
	{
		return Integer.parseInt(getValue(key));
	}
	
	protected double getDouble(String key)
	{
		return Double.parseDouble(getValue(key));
	}
	
	protected boolean getBool(String key)
	{
		String value = getValue(key).toLowerCase();
		if(value.equals("1")) return true;
		else if(value.equals("true")) return true;
		else if(value.equals("set")) return true;
		else if(value.equals("on")) return true;
		return false;
	}
	
	private void loadPropertyBag()
	{
		if(_useXMLBag)
		{
			//TODO Implement IProfile property system
		}
		else
		{
			loadManualDefinitions();
		}
	}
}
