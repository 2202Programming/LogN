package PID.tester;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AutoPIDTesterWindow {
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel outerPanel, sidePanel;
	private JLabel currentlyTesting, bestCombo;
	private JLabel combosTested;
	
	public static final int WIDTH=600, HEIGHT=400;
	
	public static AutoPIDTesterWindow window;
	private static AutoPIDTesterEngine engine;
	public static boolean shouldSetValues=false;
	
	public static void main(String[] args) {
		window=new AutoPIDTesterWindow();
		engine=new AutoPIDTesterEngine();
		shouldSetValues=true;
		window.runLoop();
	}
	
	public void runLoop() {
		double lastUpdateTime=System.currentTimeMillis();
		double lastSecondTime=lastUpdateTime;
		final long updatesPerSecond=900;
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
			stop(0.001);
		}
	}

	public AutoPIDTesterWindow() {
		frame=new JFrame("Auto PID tester");
		mainPanel=new JPanel();
		outerPanel=new JPanel();
		sidePanel=new JPanel();
		currentlyTesting=new JLabel("Currently Testing: ");
		bestCombo=new JLabel("Best Combo: ");
		combosTested=new JLabel("Combos Tested: ");
		outerPanel.add(mainPanel);
		outerPanel.add(sidePanel);
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		sidePanel.setPreferredSize(new Dimension(300, HEIGHT));
		sidePanel.add(currentlyTesting);
		sidePanel.add(combosTested);
		sidePanel.add(bestCombo);
		frame.add(outerPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void draw() {
		Graphics2D g=(Graphics2D)mainPanel.getGraphics();
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
	
	public void setInfo(String currentlyTesting, String bestCombo, int combosTested) {
		this.currentlyTesting.setText("Currently Testing: "+currentlyTesting);
		this.bestCombo.setText("Best Combo: "+bestCombo);
		this.combosTested.setText("Number of Combos Tested: "+combosTested);
	}

}