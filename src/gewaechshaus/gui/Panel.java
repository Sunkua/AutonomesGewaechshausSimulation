package gewaechshaus.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;


public class Panel extends JPanel{

	SubPanel Kopfzeile;
	SubPanel MainFrame;
	
	public Panel(String Name) {
		Kopfzeile = new SubPanel(Name);
		MainFrame = new SubPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(MainFrame, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
						.addComponent(Kopfzeile, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(Kopfzeile, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(0)
					.addComponent(MainFrame, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
		);
		Kopfzeile.setLayout(new BoxLayout(Kopfzeile, BoxLayout.LINE_AXIS));
		setLayout(groupLayout);
	}
}
