package gewaechshaus.gui;

import javax.swing.*;
import java.awt.*;

enum GuiGewaechshausStatus{
	none,
	initDone,
	running
}

enum GuiGewaechshausEvents{
	start
}

public class GuiGewaechshaus extends JPanel{

	private int border = 8;
	private int canvisBorder = 10;
	private Canvas canvas;
	private GuiGewaechshausStatus status = GuiGewaechshausStatus.none;

	public GuiGewaechshaus() {
		// TODO Auto-generated constructor stub
	}
	public void paintComponent(Graphics g) {

		// We need a bit more control over gfx here. Casting to advanced class. This is ok.
		Graphics2D g2d = (Graphics2D) g;
		
		// Fill background
		g2d.setColor( Color.lightGray);
        g2d.fillRect(border, border, getWidth()-2*border, getHeight()-2*border);
	}
	public void init(){
		toggleEvent(GuiGewaechshausEvents.start);		
	}
	
	private void toggleEvent(GuiGewaechshausEvents e){
		switch(status){
		case none:
			if(GuiGewaechshausEvents.start == e){
				canvas = new Canvas();
				canvas.setBounds(border+canvisBorder, border+canvisBorder, getWidth()-2*border-canvisBorder, getHeight()-2*border-canvisBorder);	
				add(canvas);			
			}
			break;
		case initDone:			

			break;
		}
	}
}
