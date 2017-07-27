package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

/**
 * Erstellt einen Auftrag zum Abladen der Ernte
 */
public class Abladen extends Unterauftrag {

	private Abladestation abladestation;

	/**
	 * Konstruktor für einen Abladen-Unterauftrag
	 *
	 * @param rLeitsystem
	 *            Das Roboterleitsystem für die Berechnung der Wege
	 * @param abladestation
	 *            Die Abladestation an der abgeladen werden soll
	 */
	public Abladen(Roboterleitsystem rLeitsystem, Abladestation abladestation) {
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen.");
		this.roboterleitsystem = rLeitsystem;
		this.abladestation = abladestation;
		this.zustand = 0;
		List<Position> freieNachbarFelderVonAbladestation = roboterleitsystem
				.getFreieNachbarFelderVon(abladestation.getPosition());
		zielPosition = (Position) freieNachbarFelderVonAbladestation.toArray()[0];
	}

	/**
	 * Berechnet die Zielposition, die vom Roboter angefahren werden soll. Soll
	 * immer eine Nachbarposition der eingegebenen Zielposition sein
	 *
	 * @return Gibt die Position des Ziels zurück
	 */
	private Position berechneZielPosition() {
		Position p;
		List<Position> freieNachbarFelderVonAbladestation = roboterleitsystem
				.getFreieNachbarFelderVon(abladestation.getPosition());
		try {
			p = (Position) freieNachbarFelderVonAbladestation.toArray()[0];
		} catch (Exception e) {
			p = null;
		}
		return p;
	}

	/**
	 * Startet die Ausführung des Auftrags.
	 * @param roboter Roboter für den Auftrag
	 */
	@Override
	public void ausfuehren(Roboter roboter) {
		this.status = UnterauftragsStatus.ausfuehrend;
		switch (zustand) {
		// Initialisiere und fahre eventuell schon erste Position an
		case 0:
			roboter.addObserver(this);
			roboter.setRoboterStatus(RoboterStatus.eBeschaeftigt);
			abladestation.setStatus(AbladestationStatus.besetzt);
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
		// Lade ab und gebe Station wieder frei
		case 2:
			roboter.ladePflanzenAb(abladestation);
			abladestation.setStatus(AbladestationStatus.frei);
			Logging.log(this.getClass().getName(), Level.INFO, "Lade Pflanzen ab und gebe Abladestations wieder frei");
			roboter.deleteObserver(this);
			roboter.setRoboterStatus(RoboterStatus.eBereit);
			this.status = UnterauftragsStatus.beendet;
			// Unterauftrag abgeschlossen Auftrag benachrichtigen
			setChanged();
			notifyObservers();
			break;
		}
	}

	/**
	 * Fährt den Roboter in Richtung einer seiner Nachbarpositionen
	 *
	 * @param roboter
	 *            Roboter der fahren soll
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
	 * Updatefunktion zur Aktualisierung
	 * @param o Uhr
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Uhr) {
			ausfuehren(this.roboter);
		}
	}
}
