package PID.tester;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import PID.AutoPIDTuner;

public class AutoPIDTesterEngine {
	
	private double angle=0;
	private RobotTurnSim sim=new RobotTurnSim();
	private AutoPIDTuner tuner=new AutoPIDTuner(sim);
	
	public AutoPIDTesterEngine() {
		sim.startReset(79399);
	}
	
	public void update() {
		tuner.update();
		sim.update();
		angle=sim.getAngle();
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, AutoPIDTesterWindow.WIDTH, AutoPIDTesterWindow.HEIGHT);
		g.setColor(Color.lightGray);
		g.fillRect(AutoPIDTesterWindow.WIDTH/2-1, AutoPIDTesterWindow.HEIGHT/6, 1, AutoPIDTesterWindow.HEIGHT/6*2);
		AffineTransform at=new AffineTransform();
		at.rotate(angle, AutoPIDTesterWindow.WIDTH/2, AutoPIDTesterWindow.HEIGHT/2);
		g.setTransform(at);
		g.setColor(Color.red);
		g.fillRect(AutoPIDTesterWindow.WIDTH/3, AutoPIDTesterWindow.HEIGHT/3, AutoPIDTesterWindow.WIDTH/3, AutoPIDTesterWindow.HEIGHT/3);
		g.setColor(new Color(150, 0, 0));
		g.fillRect(AutoPIDTesterWindow.WIDTH/2-2, AutoPIDTesterWindow.HEIGHT/3, 4, AutoPIDTesterWindow.HEIGHT/12);
		at.rotate(-angle,AutoPIDTesterWindow.WIDTH/2, AutoPIDTesterWindow.HEIGHT/2);
	}
	
}
