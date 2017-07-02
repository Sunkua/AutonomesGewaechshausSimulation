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
    private Position zielPosition;

    public Abladen(Roboterleitsystem rLeitsystem, Abladestation abladestation) {
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen.");
        this.roboterleitsystem = rLeitsystem;
        this.abladestation = abladestation;
        this.zustand = 0;
        List<Position> freieNachbarnVonPflanze = roboterleitsystem.getFreieNachbarFelderVon(abladestation.getGridPosition());
        zielPosition = (Position) freieNachbarnVonPflanze.toArray()[0];
    }

    private Position berechneZielPosition() {
        List<Position> freieNachbarnVonPflanze = roboterleitsystem.getFreieNachbarFelderVon(abladestation.getGridPosition());
        return (Position) freieNachbarnVonPflanze.toArray()[0];
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
                abladestation.setStatus(AbladestationStatus.besetzt);
                // Prüfe ob Zielposition == null, falls ja berechne neu und prüfe erneut ob 0, falls ja dann warte
                if (zielPosition == null) {
                    zielPosition = berechneZielPosition();
                    if (zielPosition == null) {
                        roboter.warte();
                        break;
                    }
                }
                fahreZuNachbarposition(roboter);
                zustand++;
                Logging.log(this.getClass().getName(), Level.INFO, "Initialisiere und beginne Fahrt zu Position: " + roboter.getPosition().toString());
                break;
            // Fahre zu Position
            case 1:
                if (!roboter.getPosition().equals(zielPosition)) {
                    fahreZuNachbarposition(roboter);
                } else {
                    zustand++;
                }
                Logging.log(this.getClass().getName(), Level.INFO, "Roboter fährt zu Position: " + roboter.getPosition().toString());
                break;
            // scanne
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
     * Fährt ein Nachbarkästchen des Roboters an.
     *
     * @param roboter Roboter aus der Roboterverwaltung der zur Position fahren soll
     */
    private void fahreZuNachbarposition(Roboter roboter) {
        ArrayList<Position> wegListe;
        try {
            wegListe = roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition);


            // Roboter-Position aus Liste entfernen
            if (wegListe.size() > 1) {
                wegListe.remove(wegListe.size() - 1);
            }
            Position nPos = roboter.getPosition();
            nPos = wegListe.get(wegListe.size() - 1);

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


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Clock) {
            ausfuehren(this.roboter);
        }
    }
}
