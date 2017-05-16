package gewaechshaus.gui;

import javax.swing.*;

import gewaechshaus.gui.Panel;

import java.awt.BorderLayout;
import java.util.ArrayList;


enum TerminalStatus{
	none,
	idle,
	ernte, erntePflanze, ernteSorte, ernteReif,
	scanne, scannePflanze, scanneSorte,
	hinzufuegen,
	entfernen
}

enum AuswahlStatus{
	
}

enum GuiBedinterminalEvents{
	start,
	abbruch,
	weiter,
	BtnErnten,
	BtnScannen,
	BtnHinzufügen,
	BtnEntfernen,
	BtnSorte
}

public class GuiBedinterminal extends Panel{

	private TerminalStatus status = TerminalStatus.none;
	BoxLayout Layout = new BoxLayout(this, BoxLayout.Y_AXIS);
	private JPanel AktPanel;

	public GuiBedinterminal(String Name) {
		super(Name);
		MainFrame.setLayout(new BorderLayout());
	}
	
	public void init(){
		toggleEvent(GuiBedinterminalEvents.start);		
	}
	public void GeheZurSeite(JPanel Seite){
		if(AktPanel != null){
			MainFrame.remove(AktPanel);
			MainFrame.revalidate();
			MainFrame.repaint();
		}
		AktPanel = Seite;
		MainFrame.add(AktPanel, BorderLayout.CENTER);
		MainFrame.repaint();	
	}
	
	public void toggleEvent(GuiBedinterminalEvents e){
		if(GuiBedinterminalEvents.abbruch == e ){
			status = TerminalStatus.idle;
			GeheZurSeite(new Bedinterminal_Idle(this));		
		}
		switch(status){
		case none:
			if(GuiBedinterminalEvents.start == e){
				status = TerminalStatus.idle;
				GeheZurSeite(new Bedinterminal_Idle(this));	
			}
			break;
		case idle:			
				if(GuiBedinterminalEvents.BtnErnten == e){
					status = TerminalStatus.ernte;
					GeheZurSeite(new Bedinterminal_Ernten(this));		
				}
				else if(GuiBedinterminalEvents.BtnScannen == e){
					status = TerminalStatus.scanne;
					GeheZurSeite(new Bedinterminal_Scannen(this));	
					
				}
				else if(GuiBedinterminalEvents.BtnHinzufügen == e){
					status = TerminalStatus.hinzufuegen;
					
				}
				else if(GuiBedinterminalEvents.BtnEntfernen == e){
					status = TerminalStatus.entfernen;
					
				}
			break;
		case ernte:	
			if(GuiBedinterminalEvents.BtnSorte == e){
				GeheZurSeite(new Bedinterminal_WähleSorte(this));	
			}
			break;
		case scanne:
			if(GuiBedinterminalEvents.BtnSorte == e){
				GeheZurSeite(new Bedinterminal_WähleSorte(this));	
			}	
			break;
		case hinzufuegen:	
			break;
		case entfernen:	
			break;
		}
	}


}
