package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.*;

public class Roboter {
	
	private static final Logger log = Logger.getLogger( Roboter.class.getName() );
	
    private double Batteriestatus;
    private double Fuellstand;
    private RoboterStatus Status;
    private Roboterleitsystem roboterleitsystem;
    
    public Roboter() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    private boolean GoTo(Position RelativePos) {
        return false;

    }

    private boolean Greife() {
        return false;

    }

    private boolean Schneide() {
        return false;

    }

    private boolean Lade_Auf() {
        return false;

    }

    private Position GetPosition() {
        return null;

    }

    private Pflanzenverwaltung pflanzeAnalysieren() {
        return null;

    }

    private double getGewicht() {
        return Batteriestatus;

    }

    private void setFuellstand() {

    }

    private void AddStoerung(Stoerung stoerung) {

    }

    public void setAuftrag(Unterauftrag unterauftrag) {

    }

    public RoboterStatus GetStatus() {
        return Status;

    }

    private void ErledigeUnterauftrag() {

    }

    private void BerechneWeg(Position position) {

    }

    private void NeuerAuftrag(Unterauftrag unterauftrag) {

    }
}
