package PID;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PIDValues {
	public double kp=0;
	public double ki=0;
	public double kd=0;// Start with Daniel the N00B's KD of 0
	
	public PIDValues(double kp, double ki, double kd) {
		this.kp=kp;
		this.ki=ki;
		this.kd=kd;
	}
	
	public String toString() {
		NumberFormat formatter=new DecimalFormat("#0.00000");
		return "<kp: "+formatter.format(kp)+", ki: "+formatter.format(ki)+", kd: "+formatter.format(kd)+">";
	}
}

