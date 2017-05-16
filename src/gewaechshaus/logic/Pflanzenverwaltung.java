package gewaechshaus.logic;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.HashMap;
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
    private Position maxGröße;
    
    public Pflanzenverwaltung() {
        pflanzenListe = new HashMap<Position, Einzelpflanze>();
    }
    
    public void setMaxGröße(int zeilen, int spalten){
    	maxGröße = new Position(zeilen, spalten);
    }
    public Position getMaxGröße(){
    	return maxGröße;
    }

    public void pflanzeHinzufuegen(Einzelpflanze ep)/* throws SecurityException, IOException*/ {
    	/* ToDo Prüfe pflanze -> dann füge sie hinzu
        Handler handler = new FileHandler(Settings.loggingFilePath);
        log.addHandler(handler);
        */
    }

    public void pflanzeEntfernen(Position p) {
    	// ToDo Entferne die Pflanze
        Einzelpflanze pflanze  = pflanzenListe.get(p);
    }

    public Einzelpflanze[] holePflanzenVonArt(PflanzenArt pa) {
        return null;

    }

    public Einzelpflanze[] holePflanzenVonStatus(PflanzenStatus ps) {
        return null;

    }

    public Einzelpflanze holePflanzeVonPosition(Position p) {
        return null;

    }

}
