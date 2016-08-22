package PID.tester;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AutoPIDTesterWindow {
	private JFrame frame;
	private JPanel mainPanel;
	private static final int WIDTH=600, HEIGHT=400;

	public static void main(String[] args) {
		AutoPIDTesterWindow window=new AutoPIDTesterWindow();
	}
	
	public AutoPIDTesterWindow() {
		frame=new JFrame("Auto PID tester");
		mainPanel=new JPanel();
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void draw() {
		Graphics2D g=(Graphics2D)frame.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, WIDTH, HEIGHT);
		g.dispose();
	}

}
