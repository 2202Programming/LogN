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
	 * We usually only would want to print the first error that occurred, as
	 * that tends to cause other ones. Once an error is printed, the rest won't
	 * be so that the first one can be fixed.
	 */
	private static boolean stopPrintingErrors=false;

	/**
	 * The maximum DebugMode for which messages are printed to SmartDashboard.
	 */
	private static DebugMode debugMode=DebugMode.DEBUG;

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
		if (SmartWriter.debugMode.compareTo(debugMode) >= 0)
			SmartDashboard.putString(name, value);
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
		if (SmartWriter.debugMode.compareTo(debugMode) >= 0)
			SmartDashboard.putBoolean(name, value);
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
		if (SmartWriter.debugMode.compareTo(debugMode) >= 0)
			SmartDashboard.putNumber(name, value);
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
	public static void putS(String name, String value) {
		putS(name,value,DebugMode.DEBUG);
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
	public static void putB(String name, boolean value) {
		putB(name,value,DebugMode.DEBUG);
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
	public static void putD(String name, double value) {
		putD(name,value,DebugMode.DEBUG);
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
		return SmartDashboard.getString(name, "");
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
		return SmartDashboard.getBoolean(name, false);
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
		return SmartDashboard.getNumber(name, 0);
	}
	
	/**
	 * Prints the error to standard output so it can be identified and debugged
	 * 
	 * @param The
	 *            exception that occurred
	 * @param The
	 *            name of the time period that the exception occurred (i. e.
	 *            Auto init) as a string to be printed
	 */
	public static void outputError(Exception e, String timeOccured) {
		// If this doesn't work, then we can try:
		// -printing to System.out instead of System.err
		// -printing to using SmartWriter (This would be more difficult because
		// Excetion.StackTrace would have to be converted to Strings)
		if (!stopPrintingErrors) {
			System.err.println("Exception occured in: "+timeOccured+".");
			e.printStackTrace(System.err);
			stopPrintingErrors=true;
		}
	}

}