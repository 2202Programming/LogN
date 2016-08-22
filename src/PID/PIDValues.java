package PID;

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
		double multiplier=1000;
		long newKP=Math.round(kp*multiplier);
		long newKI=Math.round(ki*multiplier);
		long newKD=Math.round(kd*multiplier);
		return "<kp: "+newKP/multiplier+", ki: "+newKI/multiplier+", kd: "+newKD/multiplier+">";
	}
}
