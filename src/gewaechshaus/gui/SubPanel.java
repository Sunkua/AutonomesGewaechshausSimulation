package gewaechshaus.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.*;

public class SubPanel extends JPanel{

	private String Name;
	
	public SubPanel() {
		Name = new String();
	}
	public SubPanel(String Name) {
		this.Name = Name;
	}
	

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Fill background
		if(Name.isEmpty()){
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
		else{
			g2d.setColor(Color.green);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setColor(Color.BLACK);
			g2d.drawString(Name, getWidth()/2, getHeight()/2);
		}
		g2d.setColor(Color.BLACK);
		g2d.drawLine(0, 0, getWidth(), 0);
		g2d.drawLine(0, 0, 0, getHeight());
		g2d.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
		g2d.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
		
	}
}