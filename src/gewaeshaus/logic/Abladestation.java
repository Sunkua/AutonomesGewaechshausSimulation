package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javafx.geometry.Pos;

/**
 * Implementiert die Funktionalität der Abladestation für Früchte.
 */
public class Abladestation {

	private static final Logger log = Logger.getLogger(Abladestation.class.getName());

	private int fuellstand;
	private int status; // TODO Was ist Status?
	private PflanzenArt pflanzenart;
	private Position gridPosition;
	private AblageTyp ablagetyp;

	/**
	 * Initialisiert eine Abladestation ohne Ablagetyp, Art und leerem Füllstand.
	 * 
	 * @param position Position im Gewächtshaus
	 * @throws SecurityException
	 * @throws IOException
	 */
	public Abladestation(Position position) throws SecurityException, IOException {
		Handler handler = new FileHandler(Settings.loggingFilePath);
		log.addHandler(handler);

		this.gridPosition = position;
		fuellstand = 0;
		pflanzenart = null;
		ablagetyp = null;
	}

	/**
	 * Aktualisiert den Füllstand der Abladestation.
	 * 
	 * @param fuellstand
	 *            Neuer Füllstand
	 */
	public void updateFuellstand(int fuellstand) {
		this.fuellstand = fuellstand;
		// TODO Logging hinzufügen + welches Loglevel?
	}

	/**
	 * Gibt den Füllstand der Abladestation zurück.
	 * 
	 * @return Aktueller Füllstand
	 */
	public int getFuellstand() {
		return fuellstand;
	}

	/**
	 * Leert die Station.
	 */
	public void leeren() {
		fuellstand = 0;
		status = 0;
		pflanzenart = null;
	}

	public PflanzenArt getPflanzenart() {
		return pflanzenart;
	}

	public void setPflanzenart(PflanzenArt pflanzenart) {
		this.pflanzenart = pflanzenart;
	}

	public int getStatus() {
		return status;
	}

	public Position getGridPosition() {
		return gridPosition;
	}

	public AblageTyp getAblagetyp() {
		return ablagetyp;
	}

	public void setAblagetyp(AblageTyp ablagetyp) {
		this.ablagetyp = ablagetyp;
	}

}
