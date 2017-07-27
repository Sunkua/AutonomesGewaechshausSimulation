package gewaechshaus.logic;

import java.util.Observable;
import java.util.logging.Level;

/**
 * Implementierung der Akkufunktionalität.
 */
public class Akku extends Observable {

	private double ladestand;
	private double ladestandAlt;
	private double kritischeGrenze;

	/**
	 * Generiert einen neuen Akku
	 *
	 * @param ladestand
	 *            Aktueller Ladezustand
	 * @param kritischeGrenze
	 *            Kritischer, unterer Ladezustand
	 */
	public Akku(double ladestand, double kritischeGrenze) {
		setLadestand(ladestand);
		setKritischeGrenze(kritischeGrenze);

		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG,
				"Ladestand: " + ladestand + " Kritische Grenze: " + kritischeGrenze);

		ladestandAlt = ladestand;
		aktualisieren(0.0);
	}

	/**
	 * Gibt den Ladestand des Akkus zurück
	 *
	 * @return Ladestand
	 */
	public double getLadestand() {
		return this.ladestand;
	}

	/**
	 * Setzt den aktuellen Ladezustand
	 *
	 * @param ladestand
	 *            Neuer Ladezustand
	 */
	public void setLadestand(double ladestand) {

		if (istLadestandImArbeitsbereich(ladestand)) {
			this.ladestand = ladestand;

			Logging.log(this.getClass().getSimpleName(), Level.INFO, "Neuer Ladestand gesetzt: " + ladestand);
		} else {
			Logging.log(this.getClass().getSimpleName(), Level.SEVERE,
					"Neuer Ladestand außerhalb des Gueltigkeitsbereichs!");
			throw new IllegalArgumentException("Ausserhalb des zugelassenen Bereichs");
		}

		ladestandAlt = ladestand;
		aktualisieren(0.0);
	}

	/**
	 * Prüft ob der Ladestand innerhalb seines Arbeitsbereiches 0 bis 100 ist.
	 *
	 * @return true wenn der Ladestand innerhalb des Gültigkeitsbereichs ist
	 */
	private boolean istLadestandImArbeitsbereich(double ladestand) {
		return (ladestand <= 100 && ladestand >= 0);
	}

	/**
	 * Setzt die kritische Ladestands-Grenze des Akkus ab der der Roboter zur
	 * Ladestation fahren soll
	 *
	 * @param kritischeGrenze
	 *            kritische Grenze in Prozent
	 */
	public void setKritischeGrenze(double kritischeGrenze) {

		if (istkritischeGrenzeImGueltigkeitsbereich(kritischeGrenze)) {
			this.kritischeGrenze = kritischeGrenze;

			Logging.log(this.getClass().getSimpleName(), Level.INFO,
					"Neue kritische Grenze gesetzt: " + kritischeGrenze);
		} else {
			Logging.log(this.getClass().getSimpleName(), Level.SEVERE,
					"Neue kritische Grenze außerhalb des Gueltigkeitsbereichs!");
			throw new IllegalArgumentException("Ausserhalb des zugelassenen Bereichs");
		}
	}

    public double getKritischeGrenze() {
        return kritischeGrenze;
    }

	/**
	 * Prüft ob die angegebene kritische Grenze innerhalb ihres
	 * Gültigkeitsbereiches ist
	 *
	 * @param kritischeGrenze Grenze, auf die geprüft werden soll
	 * @return Gültigkeit
	 */
	private boolean istkritischeGrenzeImGueltigkeitsbereich(double kritischeGrenze) {
		return (kritischeGrenze <= 100 && kritischeGrenze >= 0);
	}

	/**
	 * Fragt ab ob der Ladezustand des Akkus kritisch ist.
	 *
	 * @return Kritischer Zustand
	 */
	public boolean istKritisch() {
		return (ladestand <= kritischeGrenze);
	}

	/**
	 * Prüft ob der Akku leer ist
	 *
	 * @return True, wenn der Akku leer ist
	 */
	public boolean istLeer() {
		return (ladestand <= 0.0);
	}

	/**
	 * Aktualisiert den Ladestand entsprechend der im Parameter angegebenen
	 * Schrittweite
	 *
	 * @param Schrittweite
	 *            Schrittweite um die der Ladestand dekrementiert werden soll
	 */
	public void aktualisieren(double Schrittweite) {
		this.ladestand = this.ladestand + Schrittweite;
		this.ladestand = Math.max(Math.min(this.ladestand, 100), 0);

		if (ladestand <= kritischeGrenze && ladestandAlt > kritischeGrenze) {
			setChanged();
			notifyObservers();
		}
		if (ladestand <= 0.0 && ladestandAlt > 0.0) {
			setChanged();
			notifyObservers();
		}
		ladestandAlt = ladestand;

	}
}
