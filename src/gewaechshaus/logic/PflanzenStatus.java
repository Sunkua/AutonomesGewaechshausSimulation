package gewaechshaus.logic;

/**
 * Definiert eine Liste der Zustände mit den Reifegraden der Früchte.
 */
public enum PflanzenStatus {
    eReif, eUnreif, eFaul, eKeine;

	/**
	 * Gibt eine lesbare Form des Enums zurück.
	 * @return String Wert als String
	 */
    public String toString() {
        switch(this) {
          case eReif: return "Reif";
          case eUnreif: return "Unreif";
          case eFaul: return "Faul";
          case eKeine: return "Keine";
          default: return "UNDEF";
        }
    }
}
