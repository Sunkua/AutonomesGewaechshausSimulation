package gewaeshaus.logic;

public class RelativePosition extends Position {

	int SpaltenID;
	int ReihenID;
	public RelativePosition() {
		// TODO Auto-generated constructor stub
	}
	public int getSpaltenID() {
		return SpaltenID;
	}
	public void setSpaltenID(int spaltenID) {
		SpaltenID = spaltenID;
	}
	public int getReihenID() {
		return ReihenID;
	}
	public void setReihenID(int reihenID) {
		ReihenID = reihenID;
	}
	public int getX() {
		return 0;
	}
	public int getY() {
		return 0;
	}

}
