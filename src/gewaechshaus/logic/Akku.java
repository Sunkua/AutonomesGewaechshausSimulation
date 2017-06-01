package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Implementierung der Akkufunktionalität.
 */
public class Akku {

    private static final Logger log = Logger.getLogger(Akku.class.getName());

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
            throw new IllegalArgumentException("Ladestand außerhalb des Gültigkeitsbereiches");
        }

    }

    public double getLadestand() {
        return this.ladestand;
    }

    private boolean istLadestandImGrenzbereich(double Ladestand) {
        return (ladestand <= 100 && ladestand >= 0);
    }

    /**
     * Setzt den aktuellen Ladezustand
     *
     * @param ladestand Neuer Ladezustand
     */
    public void setLadestand(double ladestand) {
        if (istLadestandImGrenzbereich(ladestand)) {
            this.ladestand = ladestand;
        }
    }

    /**
     * Fragt ab ob der Ladezustand des Akkus kritisch ist.
     *
     * @return Kritischer Zustand
     */
    public boolean istKritisch() {
        return (ladestand < kritischeGrenze);
    }
}
