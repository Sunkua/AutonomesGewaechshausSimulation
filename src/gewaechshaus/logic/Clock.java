package gewaechshaus.logic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;


public class Clock extends java.util.Observable {

    private int schrittZeit;
    private Timer timer;

    public Clock(int schrittZeit) {
        this.schrittZeit = schrittZeit;
        this.timer = new Timer();
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" mit Abstand "+schrittZeit+" geladen");

    }

    public void stopTimer() {
        timer.cancel();
    }

    public void initTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setChanged();
                notifyObservers();
            }
        }, 0, 10);
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, "Timer initialisiert.");
    }

    public void schritt() {
        setChanged();
        notifyObservers();
    }

    public void startTimer() {
        initTimer();
    }

    public void setSchrittZeit(int schrittZeit) {
        this.schrittZeit = schrittZeit;
        Logging.log(this.getClass().getSimpleName(), Level.INFO, "Schrittzeit "+schrittZeit+" gesetzt.");
    }


}
