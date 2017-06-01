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

    public Logging(String className) throws IOException {
        //instance the logger
        logger = Logger.getLogger(Logging.class.getName());
        //instance the filehandler
        fileHandler = new FileHandler("Log.txt", true);
        logger.addHandler(fileHandler);
    }


    private static Logger getLogger(String className) throws IOException {
        logger = Logger.getLogger(className);
        //instance the filehandler
        FileHandler fileHandler = new FileHandler("Log.txt", true);
        logger.addHandler(fileHandler);
        return logger;
    }

    public static void log(String className, Level level, String msg) {
        try {
            getLogger(className).log(level, msg);
        } catch (Exception e) {

        }
    }


}
