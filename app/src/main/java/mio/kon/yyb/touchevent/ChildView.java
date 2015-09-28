package mio.kon.yyb.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import mio.kon.yyb.touchevent.util.L;

/**
 * Created by mio on 15/9/24.
 */
public class ChildView extends View {
    public ChildView(Context context) {
        this (context, null);
    }

    public ChildView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init ();
    }

    private void init() {
        setBackgroundColor (getResources ().getColor (R.color.color3));
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        L.d ("ChildView dispatchTouchEvent  "+ EventUtil.getEventName (event.getAction ()));
        return super.dispatchTouchEvent (event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.d ("ChildView onTouchEvent  "+ EventUtil.getEventName (event.getAction ()));
//        if(event.getAction () == MotionEvent.ACTION_DOWN){
//            return false;
//        }else {
//            return true;
//        }
        return super.onTouchEvent (event);
    }


}
