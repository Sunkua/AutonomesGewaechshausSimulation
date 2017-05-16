package gewaechshaus.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;

enum GuiGewaechshausStatus{
	none,
	initDone,
	running
}

enum GuiGewaechshausEvents{
	start
}

public class GuiGewaechshaus extends Panel{

	private Canvas canvas;
	private GuiGewaechshausStatus status = GuiGewaechshausStatus.none;

	public GuiGewaechshaus(String Name) {
		super(Name);
	}

	public void init(){
		toggleEvent(GuiGewaechshausEvents.start);		
	}
	
	private void toggleEvent(GuiGewaechshausEvents e){
		switch(status){
		case none:
			if(GuiGewaechshausEvents.start == e){
				canvas = new Canvas();
				JButton b = new JButton("hallo");
				GroupLayout groupLayout = new GroupLayout(MainFrame);
				groupLayout.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(canvas, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
								.addContainerGap())
					);
					groupLayout.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(canvas, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
								.addContainerGap())
					);
				MainFrame.setLayout(groupLayout);
				
			}
			break;
		case initDone:			

			break;
		}
	}
}
