package gewaechshaus.gui;

import javax.swing.*;

import gewaechshaus.gui.Panel;
import gewaechshaus.logic.Pflanzenverwaltung;

import java.awt.BorderLayout;


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
	OK,
	BtnErnten,
	BtnScannen,
	BtnHinzufügen,
	BtnEntfernen,
    BtnSorte,
    speichereZustand,
    ladeZustand
}

public class GuiBedinterminal extends Panel{

    BoxLayout Layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    private Pflanzenverwaltung pflanzenverwaltung;
	private TerminalStatus status = TerminalStatus.none;
	private JPanel AktPanel;

	public GuiBedinterminal(String Name, Pflanzenverwaltung p) {
		super(Name);
		MainFrame.setLayout(new BorderLayout());
		pflanzenverwaltung = p;
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
					if(GuiBedinterminalEvents.BtnHinzufügen == e){
						GeheZurSeite(new Bedinterminal_Hinzufügen(this));	
					}
					
				}
				else if(GuiBedinterminalEvents.BtnEntfernen == e){
					status = TerminalStatus.entfernen;
					if(GuiBedinterminalEvents.BtnEntfernen == e){
                        GeheZurSeite(new Bedinterminal_Entfernen(this));
                    }

                } else if (GuiBedinterminalEvents.speichereZustand == e) {
                    pflanzenverwaltung.pflanzenverwaltungZustandInDateiSpeichern();
                } else if (GuiBedinterminalEvents.ladeZustand == e) {
                    pflanzenverwaltung.pflanzenVerwaltungZustandAusDateiLesen();
                }
			break;
		case ernte:	
			if(GuiBedinterminalEvents.BtnSorte == e){
				GeheZurSeite(new Bedinterminal_WähleArt(this));	
			}
			break;
		case scanne:
			if(GuiBedinterminalEvents.BtnSorte == e){
				GeheZurSeite(new Bedinterminal_WähleArt(this));	
			}	
			break;
		case hinzufuegen:
			if(GuiBedinterminalEvents.OK == e){
				status = TerminalStatus.idle;
				Bedinterminal_Hinzufügen t = (Bedinterminal_Hinzufügen)AktPanel;
				pflanzenverwaltung.pflanzeHinzufuegen(t.getPflanze());
				GeheZurSeite(new Bedinterminal_Idle(this));	
			}	
			break;
		case entfernen:	
			if(GuiBedinterminalEvents.OK == e){
				status = TerminalStatus.idle;
				Bedinterminal_Entfernen t = (Bedinterminal_Entfernen)AktPanel;
				pflanzenverwaltung.pflanzeEntfernen(t.getPosition());
				GeheZurSeite(new Bedinterminal_Idle(this));	
			}	
			break;
		}
	}


}
