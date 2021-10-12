package log_workers;

import java.util.logging.Logger;

public class SystemLog {
    private static final Logger log = Logger.getLogger(SystemLog.class.getName());

    public void loggerTestOutput(String logMessage)
    {
        log.info(logMessage);
    }
}
