package Gewaechshaus.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class GuiGewaechshaus extends JPanel{

	public GuiGewaechshaus() {
		// TODO Auto-generated constructor stub
	}
	public void paintComponent(Graphics g) {

		// We need a bit more control over gfx here. Casting to advanced class. This is ok.
		Graphics2D g2d = (Graphics2D) g;
		
		// Fill with gradient background
		g2d.draw(new Line2D.Double(0,0, getWidth(), 0));
		g2d.draw(new Line2D.Double(0,0, 0, getHeight()));
		g2d.draw(new Line2D.Double(getWidth()-1,0, getWidth()-1, getHeight()));
		g2d.draw(new Line2D.Double(0,getHeight()-1, getWidth(), getHeight()-1));
        //g2d.setPaint(new GradientPaint(0, 0, Color.white, 0, getHeight(), Color.gray));
        //g2d.fillRect(0, 0, getWidth(), getHeight());
	}

}
