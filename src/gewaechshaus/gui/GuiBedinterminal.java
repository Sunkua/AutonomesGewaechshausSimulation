package gewaechshaus.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


enum GuiBedinterminalStatus{
	none,
	idle,
	ernte, erntePflanze, ernteSorte, ernteReif,
	scanne, scannePflanze, scanneSorte,
	hinzufuegen,
	entfernen
}

enum GuiBedinterminalEvents{
	start
}

public class GuiBedinterminal extends JPanel{

	private int border = 8;

	private GuiBedinterminalStatus status = GuiBedinterminalStatus.none;
	private ArrayList<JButton> button;
	BoxLayout Layout = new BoxLayout(this, BoxLayout.Y_AXIS);

	public GuiBedinterminal() {
		// TODO Auto-generated constructor stub
	}
	
	public void paintComponent(Graphics g) {
		// We need a bit more control over gfx here. Casting to advanced class. This is ok.
		Graphics2D g2d = (Graphics2D) g;
		
		// Fill background
		g2d.setColor(Color.lightGray);
        g2d.fillRect(border, border, getWidth()-2*border, getHeight()-2*border);

	}
	public void init(){
		toggleEvent(GuiBedinterminalEvents.start);		
	}
	
	private void toggleEvent(GuiBedinterminalEvents e){
		switch(status){
		case none:
			if(GuiBedinterminalEvents.start == e){
				status = GuiBedinterminalStatus.idle;
				JButton b1 = new JButton("Ernten");
				JButton b2 = new JButton("Scanen");
				JButton b3 = new JButton("Hinzufuegen");
				JButton b4 = new JButton("Entfernen");
				add(Box.createVerticalGlue());	
				add(b1);
				add(Box.createVerticalGlue());	
				add(b2);
				add(Box.createVerticalGlue());	
				add(b3);	
				add(Box.createVerticalGlue());
				add(b4);
				add(Box.createVerticalGlue());
				b1.setAlignmentX(JButton.CENTER_ALIGNMENT);
				b2.setAlignmentX(JButton.CENTER_ALIGNMENT);
				b3.setAlignmentX(JButton.CENTER_ALIGNMENT);
				b4.setAlignmentX(JButton.CENTER_ALIGNMENT);
				setLayout(Layout);
			}
			break;
		case idle:			

			break;
		}
	}


}
