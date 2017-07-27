package gewaechshaus.logic;

/**
 * Enum zur Definition der Art der Pflanze.
 */
public enum PflanzenArt {
	eTomate, eGurke;

	/**
	 * Gibt eine lesbare Form des Enums zurück.
	 *
	 * @return String Wert als String
	 */
	public String toString() {
		switch (this) {
		case eTomate:
			return "Tomate";
		case eGurke:
			return "Gurke";
		default:
			return "UNDEF";
		}
	}
}
