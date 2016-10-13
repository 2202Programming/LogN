package comms;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//done, untested --SecondThread

/**
 * A class that handles the reading from and writing to SmartDashboard. <br>
 * <br>
 * 
 * Hope you guys like the comments<br>
 * --SecondThread
 */
public class SmartWriter {

	/**
	 * The maximum DebugMode for which messages are printed to SmartDashboard.
	 */
	public static DebugMode debugMode=DebugMode.DEBUG;

	/**
	 * Sets the field with name <i>name</i> on the SmartDashboard to
	 * <i>value</i>. <br>
	 * <br>
	 * Preconditions: <i>debugMode</i> is at least as general as the debug mode
	 * that has been set. If this is not the case, nothing will be printed. <br>
	 * 
	 * Postconditions: The value of the field with the name <i>name</i> on the
	 * SmartDashboard will be set to <i>value</i>.
	 * 
	 * @param name
	 *            The name of the field (the black bold text to the field's
	 *            left)
	 * @param value
	 *            The value of the field (the text in the text box for that
	 *            field)
	 * @param debugMode
	 *            The least general debug mode that this message should be
	 *            displayed on.
	 */
	public static void putS(String name, String value, DebugMode debugMode) {
		if (SmartWriter.debugMode.compareTo(debugMode)<=0) SmartDashboard.putString(name, value);
	}

	/**
	 * Sets the field with name <i>name</i> on the SmartDashboard to
	 * <i>value</i>. <br>
	 * <br>
	 * Preconditions: <i>debugMode</i> is at least as general as the debug mode
	 * that has been set. If this is not the case, nothing will be printed. <br>
	 * 
	 * Postconditions: The value of the field with the name <i>name</i> on the
	 * SmartDashboard will be set to <i>value</i>.
	 * 
	 * @param name
	 *            The name of the field (the black bold text to the field's
	 *            left)
	 * @param value
	 *            The value of the field (the text in the text box for that
	 *            field)
	 * @param debugMode
	 *            The least general debug mode that this message should be
	 *            displayed on
	 */
	public static void putB(String name, boolean value, DebugMode debugMode) {
		if (SmartWriter.debugMode.compareTo(debugMode)<=0) SmartDashboard.putBoolean(name, value);
	}

	/**
	 * Sets the field with name <i>name</i> on the SmartDashboard to
	 * <i>value</i>. <br>
	 * <br>
	 * Preconditions: <i>debugMode</i> is at least as general as the debug mode
	 * that has been set. If this is not the case, nothing will be printed. <br>
	 * 
	 * Postconditions: The value of the field with the name <i>name</i> on the
	 * SmartDashboard will be set to <i>value</i>.
	 * 
	 * @param name
	 *            The name of the field (the black bold text to the field's
	 *            left)
	 * @param value
	 *            The value of the field (the text in the text box for that
	 *            field)
	 * @param debugMode
	 *            The least general debug mode that this message should be
	 *            displayed on.
	 */
	public static void putD(String name, double value, DebugMode debugMode) {
		if (SmartWriter.debugMode.compareTo(debugMode)<=0) SmartDashboard.putNumber(name, value);
	}

	/**
	 * Gets and returns the value in the text box on SmartDashboard with the
	 * given name.<br>
	 * <br>
	 * Preconditions: Exactly one text box with the the name <i>name</i> exists
	 * on SmartDashboard.<br>
	 * 
	 * PostConditions: The value of the text box will be returned and nothing
	 * else will have been changed. <br>
	 * 
	 * @param name
	 *            The name of the text box of which the value should be returned
	 * @return The value in the text box with the name <i>name</i>
	 */
	public static String getS(String name) {
		return SmartDashboard.getString(name);
	}

	/**
	 * Gets and returns the value in the text box on SmartDashboard with the
	 * given name.<br>
	 * <br>
	 * Preconditions: Exactly one text box with the the name <i>name</i> exists
	 * on SmartDashboard.<br>
	 * 
	 * PostConditions: The value of the text box will be returned and nothing
	 * else will have been changed. <br>
	 * 
	 * @param name
	 *            The name of the text box of which the value should be returned
	 * @return The value in the text box with the name <i>name</i>
	 */
	public static boolean getB(String name) {
		return SmartDashboard.getBoolean(name);
	}

	/**
	 * Gets and returns the value in the text box on SmartDashboard with the
	 * given name.<br>
	 * <br>
	 * Preconditions: Exactly one text box with the the name <i>name</i> exists
	 * on SmartDashboard.<br>
	 * 
	 * PostConditions: The value of the text box will be returned and nothing
	 * else will have been changed. <br>
	 * 
	 * @param name
	 *            The name of the text box of which the value should be returned
	 * @return The value in the text box with the name <i>name</i>
	 */
	public static double getD(String name) {
		return SmartDashboard.getNumber(name);
	}

}