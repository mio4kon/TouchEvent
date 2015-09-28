package mio.kon.yyb.touchevent.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.text.MessageFormat.format;

/**
 * Created by mio on 15-9-9.
 */
public class LogPretty {

    private static final ThreadLocal<String> LOCAL_TAG = new ThreadLocal ();
    private static final int JSON_INDENT = 4;
    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    public void d(String message, Object... args) {
        log (Log.DEBUG, message, args);
    }

    public void e(String message, Object... args) {
        e (null, message, args);
    }

    public void e(Throwable throwable, String message, Object... args) {
        if (throwable != null && message != null) {
            message += " : " + throwable.toString ();
        }
        if (throwable != null && message == null) {
            message = throwable.toString ();
        }
        if (message == null) {
            message = "No message/exception is set";
        }
        log (Log.ERROR, message, args);
    }

    public void w(String message, Object... args) {
        log (Log.WARN, message, args);
    }

    public void i(String message, Object... args) {
        log (Log.INFO, message, args);
    }

    public void v(String message, Object... args) {
        log (Log.VERBOSE, message, args);
    }

    public void json(String json) {
        if (TextUtils.isEmpty (json)) {
            d ("Empty/Null json content");
            return;
        }
        try {
            if (json.startsWith ("{")) {
                JSONObject jsonObject = new JSONObject (json);
                String message = jsonObject.toString (JSON_INDENT);
                log (Log.DEBUG, message);
                return;
            }
            if (json.startsWith ("[")) {
                JSONArray jsonArray = new JSONArray (json);
                String message = jsonArray.toString (JSON_INDENT);
                log (Log.DEBUG, message);
            }
        } catch (JSONException e) {
            log (Log.ERROR, e.getCause ().getMessage () + "\n" + json);
        }
    }

    private synchronized void log(int logType, String msg, Object... args) {

        if (L.getLogLevel () == L.LogLevel.NONE) {
            return;
        }
        String tag = getClassName ();
        String message = createMessage (msg, args);
        logTopBorder (logType, tag);
        logHeaderContent (logType, tag);
        logDivider (logType, tag);

        logContent (logType, tag, message);

        logBottomBorder (logType, tag);
    }

    private String getClassName() {
        StackTraceElement methodStack = (new Exception ()).getStackTrace ()[4];
        String result = methodStack.getClassName ();
        int lastIndex = result.lastIndexOf (".");
        result = result.substring (lastIndex + 1, result.length ());
        return result;
    }

    private static String callMethodAndLine() {
        StringBuffer sb = new StringBuffer ();
        StackTraceElement thisMethodStack = (new Exception ()).getStackTrace ()[5];
        sb.append (thisMethodStack.getClassName () + ".");
        sb.append (thisMethodStack.getMethodName ());
        sb.append ("(" + thisMethodStack.getFileName ());
        sb.append (":" + thisMethodStack.getLineNumber () + ")  ");
        return sb.toString ();
    }


    private String createMessage(String message, Object... args) {
        return args.length == 0 ? message : format (message, args);
    }


    private void logTopBorder(int logType, String tag) {
        logChunk (logType, tag, TOP_BORDER);
    }

    private void logDivider(int logType, String tag) {
        logChunk (logType, tag, MIDDLE_BORDER);
    }

    private void logBottomBorder(int logType, String tag) {
        logChunk (logType, tag, BOTTOM_BORDER);
    }

    private void logHeaderContent(int logType, String tag) {
        StackTraceElement[] trace = Thread.currentThread ().getStackTrace ();
        logChunk (logType, tag,
                  HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread ().getName ());
        logDivider (logType, tag);
        String level = "";

        StringBuilder builder = new StringBuilder ();
        builder.append ("║ ")
               .append (level)
               .append (callMethodAndLine ());
        logChunk (logType, tag, builder.toString ());
    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split (System.getProperty ("line.separator"));
        for(String line : lines) {
            logChunk (logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }


    private void logChunk(int logType, String tag, String chunk) {
        switch (logType) {
            case Log.ERROR:
                Log.e (tag, chunk);
                break;
            case Log.INFO:
                Log.i (tag, chunk);
                break;
            case Log.VERBOSE:
                Log.v (tag, chunk);
                break;
            case Log.WARN:
                Log.w (tag, chunk);
                break;
            case Log.ASSERT:
                Log.wtf (tag, chunk);
                break;
            case Log.DEBUG:
            default:
                Log.d (tag, chunk);
                break;
        }
    }
}
