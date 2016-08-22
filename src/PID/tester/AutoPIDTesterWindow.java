package PID.tester;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AutoPIDTesterWindow {
	private JFrame frame;
	private JPanel mainPanel;
	public static final int WIDTH=600, HEIGHT=400;
	private static AutoPIDTesterWindow window;
	private static AutoPIDTesterEngine engine;
	public static void main(String[] args) {
		window=new AutoPIDTesterWindow();
		engine=new AutoPIDTesterEngine();
		window.runLoop();
	}
	
	public void runLoop() {
		double lastUpdateTime=System.currentTimeMillis();
		double lastSecondTime=lastUpdateTime;
		final long updatesPerSecond=60;
		final double timeBetweenUpdates=1000.0/updatesPerSecond, timeBetweenSeconds=1000;
		int renders=0, updates=0;
		while (true) {
			if (System.currentTimeMillis()>lastSecondTime+timeBetweenSeconds) {
				frame.setTitle("Updates: "+updates+"    FPS: "+renders);
				renders=0;
				updates=0;
				lastSecondTime+=timeBetweenSeconds;
			}
			if (System.currentTimeMillis()>lastUpdateTime+timeBetweenUpdates) {
				engine.update();
				updates++;
				lastUpdateTime+=timeBetweenUpdates;
			}

			renders++;
			draw();
			stop(0.005);
		}
	}

	public AutoPIDTesterWindow() {
		frame=new JFrame("Auto PID tester");
		mainPanel=new JPanel();
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void draw() {
		Graphics2D g=(Graphics2D)frame.getGraphics();
		g.setColor(Color.black);
		BufferedImage toDraw=new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2=(Graphics2D)toDraw.getGraphics();
		engine.render(g2);
		g2.dispose();
		g.drawImage(toDraw, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
	}

	public void stop(double seconds) {
		try {
			Thread.sleep((long)(seconds*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
