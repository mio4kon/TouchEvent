package mio.kon.yyb.touchevent.util;

/**
 * Created by mio on 15-9-9.
 */
public class L {


    public static final int LEVEL_NO_LOG = 10;

    private static LogLevel LOG_LEVEL = LogLevel.FULL; //默认都打印

    public static void setLogLevel(LogLevel level) {
        LOG_LEVEL = level;
    }

    public static LogLevel getLogLevel() {
        return LOG_LEVEL;
    }

    private static final LogPretty printer = new LogPretty ();

    public static void d(String message, Object... args) {
        printer.d (message, args);
    }

    public static void e(String message, Object... args) {
        printer.e (null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e (throwable, message, args);
    }

    public static void i(String message, Object... args) {
        printer.i (message, args);
    }
    public static void v(String message, Object... args) {
        printer.v (message, args);
    }

    public static void w(String message, Object... args) {
        printer.w (message, args);
    }

    public static void json(String json) {
        printer.json (json);
    }


    public enum LogLevel {

        /**
         * Prints all logs
         */
        FULL,

        /**
         * No log will be printed
         */
        NONE
    }
}
