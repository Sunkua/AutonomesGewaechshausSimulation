package gewaeshaus.logic;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.List;
import java.util.logging.*;

public class Roboterleitsystem {
	
	private static final Logger log = Logger.getLogger( Roboterleitsystem.class.getName() );

    private List<Roboter> roboterListe;
    private List<Auftrag> auftragsListe;
    private Abladestation abladestation;
    private Ladestation ladestation;
    private Abladestation abladestation2;


    public Roboterleitsystem() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    public boolean operationAbbrechen(int ID) {

        return true;
    }

    public void abladeStationDefinieren() {

    }

    public Unterauftrag getUnterauftrag(int ID) {
        return null;


    }


    public void setRoboterStatus(Roboter roboter, RoboterStatus status) {

    }


    public int neuerAuftrag(Auftrag auftrag) {
        return 0;

    }


    private void warte() {

    }

    private void sendeMeldung() {

    }


    public void addAuftrag(Auftrag auftrag) {

    }


}
