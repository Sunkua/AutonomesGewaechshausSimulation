package gewaechshaus.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import gewaechshaus.gui.GuiBedinterminal;
import java.awt.Color;

public class Bedinterminal_Ernten extends JPanel {
	
	private GuiBedinterminal Terminal;

	public Bedinterminal_Ernten(GuiBedinterminal t) {
		Terminal = t;
		
		JButton btnArt = new JButton("Alle einer Art");
		btnArt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.BtnSorte);
			}
		});
		
		JButton btnReife = new JButton("Alle mit gleicher Reife");
		btnReife.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnAlle = new JButton("Alle");
		
		JButton btnAbbrechen = new JButton("<< Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.abbruch);
			}
		});
		
		JLabel lblWonachWollenSie = new JLabel("Welche Pflanzen wollen Sie Ernten?");
		
		JButton btnEineBestimmte = new JButton("Eine Bestimmte");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblWonachWollenSie, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnArt, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(btnReife, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAbbrechen, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addGap(234))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnEineBestimmte, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
							.addGap(12)
							.addComponent(btnAlle, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWonachWollenSie)
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(btnArt)
						.addComponent(btnReife))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAlle)
						.addComponent(btnEineBestimmte))
					.addPreferredGap(ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
					.addComponent(btnAbbrechen, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
}