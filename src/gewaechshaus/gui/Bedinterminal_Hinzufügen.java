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

public class Bedinterminal_Hinzufügen extends JPanel {
	
	private GuiBedinterminal Terminal;
	private Einzelpflanze pflanze = new Einzelpflanze();
	public Einzelpflanze getPflanze(){
		return pflanze;
	}

	public Bedinterminal_Hinzufügen(GuiBedinterminal t) {
		Terminal = t;
		
		JButton btnEntfernen = new JButton("<< Abbrechen");
		btnEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.abbruch);
			}
		});
		
		JLabel lblWonachWollenSie = new JLabel("Pflanze hinzufügen:");
		
		JLabel lblPosition = new JLabel("Position");
		
		JLabel lblSpalte = new JLabel("Spalte");
		
		JLabel lblZeile = new JLabel("Zeile");
		
		JComboBox cbSpalte = new JComboBox();
		cbSpalte.setEditable(true);
		
		JComboBox cbZeile = new JComboBox();
		cbZeile.setEditable(true);
		
		JLabel lblArt = new JLabel("Art");
		
		JComboBox cbArt = new JComboBox();
		for (PflanzenArt art : PflanzenArt.values()){
			cbArt.addItem(art);
		}
		
		
		JLabel lblReife = new JLabel("Reife");
		
		JComboBox cbReife = new JComboBox();
		for (PflanzenStatus s : PflanzenStatus.values()){
			cbReife.addItem(s);
		}
		
		JButton btnHinzufgen = new JButton("OK");
		btnHinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pflanze.setPflanzenstatus( (PflanzenStatus) cbReife.getSelectedItem());
				pflanze.setPflanzenart( (PflanzenArt) cbArt.getSelectedItem());
				int spalte = Integer.parseInt(cbSpalte.getSelectedItem().toString());
				int zeile = Integer.parseInt(cbZeile.getSelectedItem().toString());
				pflanze.setPosition(new Position(spalte, zeile));
				Terminal.toggleEvent(GuiBedinterminalEvents.OK);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblWonachWollenSie, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnEntfernen, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(btnHinzufgen, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)))
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPosition)
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblSpalte, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbSpalte, 0, 121, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblZeile, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(cbArt, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
										.addComponent(cbZeile, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
										.addComponent(cbReife, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(234, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArt, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(399, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblReife, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(399, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWonachWollenSie)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSpalte)
								.addComponent(cbSpalte, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblZeile)
								.addComponent(cbZeile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(lblPosition)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArt)
						.addComponent(cbArt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReife)
						.addComponent(cbReife, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEntfernen, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnHinzufgen))
					.addContainerGap())
		);
		setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
}
