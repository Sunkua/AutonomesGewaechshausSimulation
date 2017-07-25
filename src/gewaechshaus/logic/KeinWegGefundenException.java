package gewaechshaus.logic;

/**
 * Eigene Exception-Klasse für den Fall, dass kein Weg gefunden wurde bei der
 * Berechnung eines Pfades von Punkt a nach Punkt b
 */
public class KeinWegGefundenException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -4348278189646857624L;

    public KeinWegGefundenException() {

    }

    /**
     * Konstruktor
     *
     * @param nachricht Nachricht in der Exception
     */
    public KeinWegGefundenException(String nachricht) {
        super(nachricht);
    }
}
