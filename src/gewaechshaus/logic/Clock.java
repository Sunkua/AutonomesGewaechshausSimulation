package gewaechshaus.logic;

import java.util.Timer;
import java.util.TimerTask;


public class Clock extends java.util.Observable {

    private int schrittZeit;
    private Timer timer;

    public Clock(int schrittZeit) {
        this.schrittZeit = schrittZeit;
        this.timer = new Timer();

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
        }, 0, schrittZeit);
    }

    public void startTimer() {
        initTimer();
    }

    public void setSchrittZeit(int schrittZeit) {
        this.schrittZeit = schrittZeit;
    }


}
