package gewaechshaus.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import gewaechshaus.gui.GuiBedinterminal;
import gewaechshaus.logic.PflanzenArt;

public class Bedinterminal_WähleArt extends JPanel {
	
	private GuiBedinterminal Terminal;

	public Bedinterminal_WähleArt(GuiBedinterminal t) {
		Terminal = t;
		
		JButton btnEntfernen = new JButton("<< Abbrechen");
		btnEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.abbruch);
			}
		});
		
		JLabel lblWonachWollenSie = new JLabel("Wählen sie eine Pflanzenart.");
		
		JButton btnWeiter = new JButton("Weiter >>");
		btnWeiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.weiter);
			}
		});
		
		JLabel label = new JLabel("Art");
		
		JComboBox cbArt = new JComboBox();
		for (PflanzenArt art : PflanzenArt.values()){
			cbArt.addItem(art);
		}
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblWonachWollenSie, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnEntfernen, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnWeiter, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(44)
							.addComponent(cbArt, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWonachWollenSie)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(cbArt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEntfernen, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnWeiter))
					.addContainerGap())
		);
		setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
}
