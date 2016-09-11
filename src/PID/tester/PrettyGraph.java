package PID.tester;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PrettyGraph extends JPanel {
	
	private static final long serialVersionUID=1L;
	
	private static final int WIDTH=300, HEIGHT=200;
	private int[] heights;
	private int currentIndex=0;
	
	private boolean needToRepaint=true;
	
	public PrettyGraph(int numberOfTries) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		heights=new int[numberOfTries];
	}
	
	public void addDataPoint(int height) {
		if (currentIndex>=heights.length) {
			return;
		}
		heights[currentIndex]=height;
		currentIndex++;
		needToRepaint=true;
	}
	
	
	public void draw() {
		if (!needToRepaint) {
			return;
		}
		needToRepaint=false;
		
		Graphics2D g=(Graphics2D)getGraphics();
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		int maxHeight=Integer.MIN_VALUE;
		for (int h:heights) {
			maxHeight=Math.max(maxHeight, h);
		}
		
		g.setColor(Color.red);
		int lineHeight=(int)(120/(float)maxHeight*HEIGHT*.8);
		g.drawLine(0, HEIGHT-lineHeight, WIDTH, HEIGHT-lineHeight);
		
		g.setColor(Color.green);
		int lines=3;
		for (int i=1; i<lines; i++) {
			g.drawLine(WIDTH*i/lines, 0, WIDTH*i/lines, HEIGHT);
		}
		
		g.setColor(Color.blue);
		float widthOfBar=WIDTH/(float)heights.length;
		for (int i=0; i<heights.length; i++) {
			int height=(int)(heights[i]/(float)maxHeight*HEIGHT*.8);
			g.fillRect((int)(i*widthOfBar), HEIGHT-height, (int)widthOfBar, height);
		}
		g.dispose();
	}
}
