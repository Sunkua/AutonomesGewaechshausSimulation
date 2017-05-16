package gewaechshaus.logic;

import javax.xml.bind.annotation.XmlRootElement;

import gewaechshaus.gui.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Pflanzenverwaltung {

    private static final Logger log = Logger.getLogger(Pflanzenverwaltung.class.getName());

    /**
     * Hält die Liste von Pflanzen inkl. deren Positionen
     */
    private HashMap<Position, Einzelpflanze> pflanzenListe;
    private Position maxGröße = new Position(0,0);
    GUI gui;

    public Pflanzenverwaltung() throws SecurityException, IOException {
        pflanzenListe = new HashMap<Position, Einzelpflanze>();
        Handler handler = new FileHandler(Settings.loggingFilePath);
        log.addHandler(handler);
        
        log.info("Pflanzenverwaltun initialisiert.");
    }

    public void pflanzeHinzufuegen(Einzelpflanze ep) {
    	pflanzenListe.put(ep.getPosition(), ep);
    	gui.updateGewächshaus();
    	
    	log.info("Pflanze " + ep.toString() + "an Position " + ep.getPosition().toString() + "Hinzugefügt");
    }

    public void pflanzeEntfernen(Position p) {
    	// ToDo Entferne die Pflanze
       Einzelpflanze pflanze  = pflanzenListe.get(p);
       pflanzenListe.remove(p);
       gui.updateGewächshaus();
        
        log.info("Pflanze " + pflanze.toString() + " an Position " + p.toString() + " entfernt.");
    }
    
    public void setMaxGröße(int zeilen, int spalten){
    	maxGröße = new Position(spalten, zeilen);
    }
    public Position getMaxGröße(){
    	return maxGröße;
    }
    
    public ArrayList<Einzelpflanze> holePflanzenVonArt(PflanzenArt pa) {
    	ArrayList<Einzelpflanze> einzelpflanzen = new ArrayList<Einzelpflanze>();
    	
    	for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
    		Einzelpflanze pflanze = entry.getValue();
    		if (pflanze.getArt() == pa) {
    			einzelpflanzen.add(pflanze);
    		}
    	}
    	
        return einzelpflanzen;
    }

    public ArrayList<Einzelpflanze> holePflanzenVonStatus(PflanzenStatus ps) {
    	ArrayList<Einzelpflanze> einzelpflanzen = new ArrayList<Einzelpflanze>();
        
    	for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
    		Einzelpflanze pflanze = entry.getValue();
    		if (pflanze.getPflanzenstatus() == ps) {
    			einzelpflanzen.add(pflanze);
    		}
    	}
    	
        return einzelpflanzen;
    }

    public Einzelpflanze holePflanzeVonPosition(Position p) {
		Einzelpflanze pflanze  = pflanzenListe.get(p);
        
        log.info("Pflanze an Position " + p.toString() + " geladen.");
        
        return pflanze;
    }
    public void setGui(GUI g){
    	gui = g;
    }

}
