package utils;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static Logger logger= Logger.getLogger(LoggerUtil.class.getName());
    static {
        try {
            // Remove console handler
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                rootLogger.removeHandler(handler);
            }
            // Configure the logger to write to a file
            FileHandler fileHandler = new FileHandler("error.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException ignored) {
        }
    }

    public static void logError(String message, Throwable throwable) {
        // Log the exception with level SEVERE
        logger.log(Level.SEVERE, message, throwable);
    }

    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }
}
