package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

/**
 * Implementiert einen Auftrag zum Laden des Akkus.
 */
public class AkkuLaden extends Unterauftrag {

	private Ladestation ladestation;

	/**
	 * Konstruktor zum Erstellen eines Unterauftrags zum Laden des Akkus
	 *
	 * @param roboterleitsystem
	 *            Leitsystem zur Berechnung der Wege
	 * @param ladestation
	 *            Ladestation an der aufgeladen werden soll
	 */
	public AkkuLaden(Roboterleitsystem roboterleitsystem, Ladestation ladestation) {
		this.ladestation = ladestation;
		this.roboterleitsystem = roboterleitsystem;
		this.zustand = 0;
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
	}

	/**
	 * Berechnet die Zielposition
	 *
	 * @return
	 */
	private Position berechneZielPosition() {
		List<Position> freieNachbarFelderVonAbladestation = roboterleitsystem
				.getFreieNachbarFelderVon(ladestation.getPosition());
		Position p;
		try {
			p = (Position) freieNachbarFelderVonAbladestation.toArray()[0];
		} catch (Exception e) {
			p = null;
		}
		return p;
	}

	/**
	 * Startet die Ausführung des Auftrags.
	 */
	@Override
	public void ausfuehren(Roboter roboter) {
		this.status = UnterauftragsStatus.ausfuehrend;
		switch (zustand) {
		// Initialisiere und fahre eventuell schon erste Position an
		case 0:
			roboter.addObserver(this);
			roboter.setRoboterStatus(RoboterStatus.eBeschaeftigt);
			ladestation.setLadestationStatus(LadestationStatus.besetzt);
			// Prüfe ob Zielposition == null, falls ja berechne neu und prüfe
			// erneut ob 0,
			// falls ja dann warte
			if (zielPosition == null) {
				zielPosition = berechneZielPosition();
				if (zielPosition == null) {
					roboter.warte();
					break;
				}
			}
			fahreZuNachbarposition(roboter);
			zustand++;
			Logging.log(this.getClass().getName(), Level.INFO,
					"Initialisiere und beginne Fahrt zu Position: " + roboter.getPosition().toString());
			break;
		// Fahre zu Position
		case 1:
			if (!roboter.getPosition().equals(zielPosition)) {
				fahreZuNachbarposition(roboter);
			} else {
				zustand++;
			}
			Logging.log(this.getClass().getName(), Level.INFO,
					"Roboter fährt zu Position: " + roboter.getPosition().toString());
			break;
		// Lade Roboter auf, bis voll geladen
		case 2:
			// ladestation.roboterLaden(roboter);
			// Lade Roboter, bis der Ladestand > 95 % ist
			roboter.setRoboterStatus(RoboterStatus.eLädt);
			if (roboter.getAkku().getLadestand() > 95f) {
				zustand++;
				ladestation.setLadestationStatus(LadestationStatus.frei);
				roboter.deleteObserver(this);

				roboter.setRoboterStatus(RoboterStatus.eBereit);
				this.status = UnterauftragsStatus.beendet;
				setChanged();
				notifyObservers();
			}
			break;
		case 3:
		}
	}

	/**
	 * Fährt den Roboter zu einer seiner Nachbarpositionen
	 *
	 * @param roboter
	 *            Roboter der zu einer seiner Nachbarpositionen fahren soll
	 */
	protected void fahreZuNachbarposition(Roboter roboter) {
		ArrayList<Position> wegListe;
		try {
			wegListe = roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition);

			// Roboter-Position aus Liste entfernen
			if (wegListe.size() > 1) {
				wegListe.remove(wegListe.size() - 1);
			}
			Position nPos = wegListe.get(wegListe.size() - 1);

			Position rPos = roboter.getPosition();
			// Fahre in Richtung der Position
			if (nPos.getReihenID() > rPos.getReihenID()) {
				roboter.fahreNachUnten();
			} else if (nPos.getReihenID() < rPos.getReihenID()) {
				roboter.fahreNachOben();
			} else if (nPos.getSpaltenID() < rPos.getSpaltenID()) {
				roboter.fahreNachLinks();
			} else if (nPos.getSpaltenID() > rPos.getSpaltenID()) {
				roboter.fahreNachRechts();
			}

			if (roboter.getPosition().equals(nPos)) {
				wegListe.remove(0);
			}

		} catch (KeinWegGefundenException e) {
			Logging.log(this.getClass().getName(), Level.WARNING, "Kein Weg gefunden.");
			roboter.warte();
		}
	}

	/**
	 * Bei jedem Tick soll ausführen aufgerufen werden, wenn der Unterauftrag
	 * aktiv ist
	 *
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Uhr) {
			ausfuehren(this.roboter);
		}
	}
}
