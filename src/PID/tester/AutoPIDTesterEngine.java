package PID.tester;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class AutoPIDTesterEngine {
	
	private double angle=0;
	private RobotTurnSim sim=new RobotTurnSim();
	
	public AutoPIDTesterEngine() {
		sim.startReset();
	}
	
	public void update() {
		sim.update();
		angle=sim.getAngle();
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, AutoPIDTesterWindow.WIDTH, AutoPIDTesterWindow.HEIGHT);
		AffineTransform at=new AffineTransform();
		at.rotate(angle, AutoPIDTesterWindow.WIDTH/2, AutoPIDTesterWindow.HEIGHT/2);
		g.setTransform(at);
		g.setColor(Color.red);
		g.fillRect(AutoPIDTesterWindow.WIDTH/3, AutoPIDTesterWindow.HEIGHT/3, AutoPIDTesterWindow.WIDTH/3, AutoPIDTesterWindow.HEIGHT/3);
		at.rotate(-angle,AutoPIDTesterWindow.WIDTH/2, AutoPIDTesterWindow.HEIGHT/2);
	}
	
}
