package gewaechshaus.logic;

import java.util.logging.Level;

/**
 * Implementierung der Akkufunktionalität.
 */
public class Akku {

    private double ladestand;
    private double kritischeGrenze;

    /**
     * Generiert einen neuen Akku
     *
     * @param ladestand  Aktueller Ladezustand
     * @param kritGrenze Kritischer, unterer Ladezustand
     */
    public Akku(double ladestand, double kritGrenze) {

        if (istLadestandImGrenzbereich(ladestand)) {
            this.kritischeGrenze = kritGrenze;
            this.ladestand = ladestand;
        } else {
        	Logging.log(this.getClass().getSimpleName(), Level.SEVERE, "Ladestand außerhalb des Gueltigkeitsbereichs!");
            throw new IllegalArgumentException("Ladestand außerhalb des Gültigkeitsbereiches");
        }
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, "Ladestand: "+ladestand+" Kritische Grenze: "+kritGrenze);

    }

    public double getLadestand() {
        return this.ladestand;
    }

    /**
     * Setzt den aktuellen Ladezustand
     *
     * @param ladestand Neuer Ladezustand
     */
    public void setLadestand(double ladestand) {

        if (istLadestandImGrenzbereich(ladestand)) {
            this.ladestand = ladestand;
            
            Logging.log(this.getClass().getSimpleName(), Level.INFO, "Neuer Ladestand gesetzt: "+ladestand);
        } else {
        	Logging.log(this.getClass().getSimpleName(), Level.SEVERE, "Neuer Ladestand außerhalb des Gueltigkeitsbereichs!");
            throw new IllegalArgumentException("Ausserhalb des zugelassenen Bereichs");
        }
    }

    private boolean istLadestandImGrenzbereich(double ladestand) {
        return (ladestand <= 100 && ladestand >= 0);
    }

    /**
     * Fragt ab ob der Ladezustand des Akkus kritisch ist.
     *
     * @return Kritischer Zustand
     */
    public boolean istKritisch() {
        return (ladestand < kritischeGrenze);
    }
    public boolean istLeer() {
        return (ladestand <= 0.0);
    }
    public void aktualisieren(double Schrittweite){
    	this.ladestand = this.ladestand + Schrittweite;
    	this.ladestand = Math.max(Math.min(this.ladestand, 100), 0);
    }
}
