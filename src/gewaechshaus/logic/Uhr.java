package gewaechshaus.logic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class Uhr extends java.util.Observable {

    private int schrittZeit;
    private ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();
    private Runnable task;
    private ScheduledFuture<?> futureTask;

    /**
     * Erstellt ein Uhr-Objekt mit einer initialen Zeit pro Schritt in ms
     *
     * @param schrittZeit
     */
    public Uhr(int schrittZeit) {
        this.schrittZeit = schrittZeit;
        task = () -> tick();
        log(Level.INFO, "Uhr initialisiert");
    }

    /**
     * Führt einen Tick der Uhr aus
     */
    public synchronized void tick() {
        setChanged();
        notifyObservers();
    }

    /**
     * Stoppt den Timer
     */
    public void stopTimer() {
        if (futureTask != null) {
            futureTask.cancel(true);
        }
        log(Level.INFO, "Simulationsuhr angehalten");
    }


    /**
     * Startet den Timer
     */
    public void startTimer() {
        if (futureTask == null) {
            futureTask = scheduler.scheduleAtFixedRate(task, 0, schrittZeit, MILLISECONDS);
        }
        log(Level.INFO, "Simulationsuhr gestartet mit Periodendauert von: " + schrittZeit + "ms");
    }

    /**
     * Setzt die Schrittzeit
     * @param schrittZeit Zeit die zwischen jedem Schritt gewartet werden soll. Angegeben in ms
     */
    public void setSchrittZeit(int schrittZeit) {
        if (schrittZeit > 0) {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            futureTask = scheduler.scheduleAtFixedRate(task, 0, schrittZeit, MILLISECONDS);
        }
        log(Level.INFO, "Periodendauer auf: " + schrittZeit + "ms gesetzt");
    }

    /**
     * Wrapper auf die Log-Methode zur Vereinfachung
     *
     * @param lv        Loglevel
     * @param nachricht Nachricht die geloggt werden soll
     */
    private void log(Level lv, String nachricht) {
        Logging.log(this.getClass().getName(), lv, nachricht);
    }


}
