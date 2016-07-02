package motors;
import edu.wpi.first.wpilibj.Spark;


/**
 * 
 */
public class SparkMotor extends IMotor {

    /**
     * Default constructor
     */
    public SparkMotor(int x) {
    	part = new Spark(x);
    }

    /**
     * 
     */
    private Spark part;

    /**
     * @param x
     */

	protected void setMotor(double x) {
		// TODO Auto-generated method stub
		
	}

}