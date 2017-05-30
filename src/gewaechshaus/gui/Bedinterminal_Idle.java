package gewaechshaus.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import gewaechshaus.gui.GuiBedinterminal;

public class Bedinterminal_Idle extends JPanel {
	
	private GuiBedinterminal Terminal;

	public Bedinterminal_Idle(GuiBedinterminal t) {
		Terminal = t;
		
		JButton btnErnten = new JButton("Ernten");
		btnErnten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.BtnErnten);
			}
		});
		
		JButton btnScannen = new JButton("Scannen");
		btnScannen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.BtnScannen);
			}
		});
		
		JButton btnHinzufgen = new JButton("Hinzufügen");
		btnHinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.BtnHinzufügen);
			}
		});
		
		JButton btnEntfernen = new JButton("Entfernen");
		btnEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Terminal.toggleEvent(GuiBedinterminalEvents.BtnEntfernen);
			}
		});

        JButton btnSpeichereZustand = new JButton("Speichere Zustand");
        btnSpeichereZustand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Terminal.toggleEvent(GuiBedinterminalEvents.speichereZustand);
            }
        });

        JButton btnLadeZustand = new JButton("Lade Zustand");
        btnLadeZustand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Terminal.toggleEvent(GuiBedinterminalEvents.ladeZustand);
            }
        });

        GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnErnten, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnHinzufgen, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnEntfernen, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(btnScannen, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnErnten, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnScannen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnHinzufgen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnEntfernen, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}

}
