package gewaechshaus.logic;

import java.util.logging.Level;

public class Ladestation {

	private Position gridPosition;
	private LadestationStatus status;

	public Ladestation(Position pos) {
		status = LadestationStatus.frei;
		gridPosition = pos;
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
	}

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
