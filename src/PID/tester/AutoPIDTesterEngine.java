package PID.tester;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class AutoPIDTesterEngine {
	
	private double angle=0;
	
	public AutoPIDTesterEngine() {
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, AutoPIDTesterWindow.WIDTH, AutoPIDTesterWindow.HEIGHT);
		AffineTransform at=new AffineTransform();
		at.rotate(System.currentTimeMillis()/1000.0, AutoPIDTesterWindow.WIDTH/2, AutoPIDTesterWindow.HEIGHT/2);
		g.setTransform(at);
		g.setColor(Color.red);
		g.fillRect(AutoPIDTesterWindow.WIDTH/4, AutoPIDTesterWindow.HEIGHT/3, AutoPIDTesterWindow.WIDTH/2, AutoPIDTesterWindow.HEIGHT/3);
		at.rotate(-angle,AutoPIDTesterWindow.WIDTH/2, AutoPIDTesterWindow.HEIGHT/2);
	}
	
}
