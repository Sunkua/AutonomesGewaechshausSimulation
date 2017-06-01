package gewaechshaus.logic;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    static Logger logger;
    public Handler fileHandler;

    public Logging() throws IOException {
        //instance the logger
        logger = Logger.getLogger(Logging.class.getName());
        //instance the filehandler
        fileHandler = new FileHandler("Log.txt", true);
        logger.addHandler(fileHandler);
    }


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

    public static void log(Level level, String msg) {
        getLogger().log(level, msg);

    }


}
