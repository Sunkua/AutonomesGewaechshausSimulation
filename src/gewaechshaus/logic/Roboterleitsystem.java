package gewaechshaus.logic;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Roboterleitsystem {
	
	private static final Logger log = Logger.getLogger( Roboterleitsystem.class.getName() );

    private List<Roboter> roboterListe;
    private List<Auftrag> auftragsListe;
    private Stack<Auftrag> auftragsStack;
    private Abladestation abladestation;
    private Ladestation ladestation;
    private Abladestation abladestation2;


    public Roboterleitsystem() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		auftragsStack = new Stack<Auftrag>();

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


    public void auftragHinzufuegen(Auftrag auftrag) {
        auftragsStack.push(auftrag);

    }


    private void warte() {

    }

    private void sendeMeldung() {

    }

}
