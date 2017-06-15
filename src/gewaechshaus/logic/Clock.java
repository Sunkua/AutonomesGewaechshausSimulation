package gewaechshaus.logic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class Clock extends java.util.Observable {

    private int schrittZeit;
    private ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();
    private Runnable task;
    private ScheduledFuture<?> futureTask;

    public Clock(int schrittZeit) {
        this.schrittZeit = schrittZeit;
        task = new Runnable() {
            @Override
            public void run() {
                setChanged();
                notifyObservers();
            }
        };
        log(Level.INFO, "Clock initialisiert");
    }

    public void stopTimer() {
        if (futureTask != null) {
            futureTask.cancel(true);
        }
        log(Level.INFO, "Simulationsuhr angehalten");
    }

    public void schritt() {
        task.run();
        log(Level.INFO, "Ein Simulationsschritt ausgeführt");
    }

    public void startTimer() {
        if (futureTask == null) {
            futureTask = scheduler.scheduleAtFixedRate(task, 0, schrittZeit, MILLISECONDS);
        }
        log(Level.INFO, "Simulationsuhr gestartet mit Periodendauert von: " + schrittZeit + "ms");
    }

    public void setSchrittZeit(int schrittZeit) {
        if (schrittZeit > 0) {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            futureTask = scheduler.scheduleAtFixedRate(task, 0, schrittZeit, MILLISECONDS);
        }
        log(Level.INFO, "Periodendauer auf: " + this.schrittZeit + "ms gesetzt");
    }

    private void log(Level lv, String msg) {
        Logging.log(this.getClass().getName(), lv, msg);
    }


}
