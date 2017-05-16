package gewaechshaus.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import gewaechshaus.gui.GuiBedinterminal;
import gewaechshaus.logic.Einzelpflanze;
import gewaechshaus.logic.PflanzenArt;
import gewaechshaus.logic.PflanzenStatus;
import gewaechshaus.logic.Position;

import java.awt.Color;

public class Bedinterminal_Entfernen extends JPanel {
	
	private GuiBedinterminal Terminal;
	private Position position = new Position();
	public Position getPosition(){
		return position;
	}

	public Bedinterminal_Entfernen(GuiBedinterminal t) {
		Terminal = t;
		
		JButton btnEntfernen = new JButton("<< Abbrechen");
		btnEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.abbruch);
			}
		});
		
		JLabel lblWonachWollenSie = new JLabel("Pflanze entfernen:");
		
		JLabel lblPosition = new JLabel("Position");
		
		JLabel lblSpalte = new JLabel("Spalte");
		
		JLabel lblZeile = new JLabel("Zeile");
		
		JComboBox cbSpalte = new JComboBox();
		cbSpalte.setEditable(true);
		
		JComboBox cbZeile = new JComboBox();
		cbZeile.setEditable(true);
		
		JButton btnHinzufgen = new JButton("OK");
		btnHinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int spalte = Integer.parseInt(cbSpalte.getSelectedItem().toString());
				int zeile = Integer.parseInt(cbZeile.getSelectedItem().toString());
				position = new Position(spalte, zeile);
				Terminal.toggleEvent(GuiBedinterminalEvents.OK);	
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblWonachWollenSie, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnEntfernen, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnHinzufgen, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblSpalte, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbSpalte, 0, 121, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblZeile, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbZeile, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(234, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPosition)
					.addContainerGap(403, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWonachWollenSie)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblPosition)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSpalte)
						.addComponent(cbSpalte, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZeile)
						.addComponent(cbZeile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEntfernen, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnHinzufgen))
					.addContainerGap())
		);
		setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
}
