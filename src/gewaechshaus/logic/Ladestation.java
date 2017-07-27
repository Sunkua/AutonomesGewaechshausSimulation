package gewaechshaus.logic;

import java.util.logging.Level;

/**
 * Implementierung einer Ladestation zum Laden der Roboter
 */
public class Ladestation {

	private Position gridPosition;
	private LadestationStatus status;

	/**
	 * Initialisiert die Ladestation an der gegebenen Position
	 * @param pos Position der Ladestation
	 */
	public Ladestation(Position pos) {
		status = LadestationStatus.frei;
		gridPosition = pos;
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
	}

	// Getter und Setter
	public Position getPosition() {
		return gridPosition;
	}

	public void setGridPosition(Position gridPosition) {
		this.gridPosition = gridPosition;
	}

	public LadestationStatus getStatus() {
		return status;
	}

	public void setLadestationStatus(LadestationStatus ls) {
		this.status = ls;
	}

}
