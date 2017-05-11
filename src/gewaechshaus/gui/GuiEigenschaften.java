package gewaechshaus.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Line2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GuiEigenschaften extends JPanel{

	private int border = 8;
	
	public GuiEigenschaften() {
		// TODO Auto-generated constructor stub
	}

	public void paintComponent(Graphics g) {

		// We need a bit more control over gfx here. Casting to advanced class. This is ok.
		Graphics2D g2d = (Graphics2D) g;
		
		// Fill background
		g2d.setColor(Color.lightGray);
        g2d.fillRect(border, border, getWidth()-4*border, getHeight()-4*border);
	}
	
}
