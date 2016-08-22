package PID.tester;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AutoPIDTesterWindow {
	private JFrame frame;
	private JPanel mainPanel;
	public static final int WIDTH=600, HEIGHT=400;

	public static void main(String[] args) {
		AutoPIDTesterWindow window=new AutoPIDTesterWindow();
		window.runLoop();
	}
	
	public void runLoop() {
		long lastUpdateTime=System.currentTimeMillis();
		long lastSecondTime=lastUpdateTime;
		final long updatesPerSecond=60;
		final long timeBetweenUpdates=1000/updatesPerSecond, timeBetweenSeconds=1000;
		int renders=0, updates=0;
		while (true) {
			
			if (System.currentTimeMillis()>lastSecondTime+timeBetweenSeconds) {
				System.out.println("Updates: "+updates+"    FPS: "+renders);
				renders=0;
				updates=0;
				lastSecondTime+=timeBetweenSeconds;
			}
			renders++;
			draw();
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
		g.fillRect(0, 0, WIDTH-5, HEIGHT-5);
		g.dispose();
	}

}
