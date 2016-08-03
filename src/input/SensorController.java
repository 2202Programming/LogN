/**
 * 
 */
package input;

/**
 * @author Daniel
 *
 */
public class SensorController {
	private static SensorController controller = null;
	private SensorController(){
		
	}
	
	public static SensorController getInstance(){
		if(controller == null){
			controller = new SensorController();
		}
		return controller;
	}
}
