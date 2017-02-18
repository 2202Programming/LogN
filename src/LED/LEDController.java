package LED;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.wpilibj.Relay;
import robot.IControl;

public class LEDController extends IControl {
	private List<Relay> redLEDs = new LinkedList<>();
	private List<Relay> blueLEDs = new LinkedList<>();
	private List<Relay> enabledLEDs = new LinkedList<>();
}
