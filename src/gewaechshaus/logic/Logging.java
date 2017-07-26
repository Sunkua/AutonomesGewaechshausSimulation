package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.*;


/**
 * Zentrale Logging-Klasse, stellt eine Methode zum generellen Logging in eine gemeinsame Datei zur Verfügung.
 *
 * @author Benjamin Junker
 * @version 08.06.2017_1
 */
public class Logging {
    private static Logger logger = null;
    public Handler fileHandler = null;


    /**
     * Initialisiert den Logger, gibt ihm einen Filehandler zum Schreiben in eine Datei und formatiert die Ausgabe.
     *
     * @throws IOException Datei konnte nicht geöffnet werden.
     */
    private Logging() throws IOException {
        logger = Logger.getLogger("Gewaechshaus");
        fileHandler = new FileHandler("Log.txt");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    /**
     * Gibt die aktuelle Logging-Instanz zurück. Falls diese noch nicht existiert wird der Konstruktor aufgerufen um eine Instanz zu erstellen.
     *
     * @return Referenz des aktuellen Loggers
     */
    private static Logger getLogger() {
        if (logger == null) {
            try {
                new Logging();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    /**
     * Schreibt eine Nachricht in das Log.
     *
     * @param className Klassenname der Ursprungsklasse
     * @param level     Loglevel der Nachricht
     * @param msg       Nachricht, die zu loggen ist
     */
    public static void log(String className, Level level, String msg) {
        if (Konstanten.loggingAn) {
            getLogger().log(level, "Class: " + className + " Message: " + msg);
        }
    }


}
