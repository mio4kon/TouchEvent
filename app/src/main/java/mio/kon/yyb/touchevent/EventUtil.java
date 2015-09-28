package mio.kon.yyb.touchevent;

import android.view.MotionEvent;

/**
 * Created by mio on 15/9/24.
 */
public class EventUtil {

    public static String getEventName(int action){
        if(action == MotionEvent.ACTION_DOWN)
            return "ACTION_DOWN";
        if(action == MotionEvent.ACTION_MOVE)
            return  "ACTION_MOVE";
        if(action == MotionEvent.ACTION_UP)
            return "ACTION_UP";
        return "Other: "+action;
    }
}
