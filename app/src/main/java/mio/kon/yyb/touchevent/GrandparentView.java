package mio.kon.yyb.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import mio.kon.yyb.touchevent.util.L;

/**
 * Created by mio on 15/9/24.
 */
public class GrandparentView extends ViewGroup {


    public GrandparentView(Context context) {
        this (context, null);

    }


    public GrandparentView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init ();
    }

    private void init() {
        setBackgroundColor (getResources ().getColor (R.color.color1));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt (0);
        LayoutParams st = child.getLayoutParams ();
        child.layout (0, 0, st.width, st.height);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.d ("GrandparentView onTouchEvent " + EventUtil.getEventName (event.getAction ()));
//        return true;
        return super.onTouchEvent (event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.d ("GrandparentView dispatchTouchEvent  " + EventUtil.getEventName (ev.getAction ()));
//        return  true;
        return super.dispatchTouchEvent (ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        L.d ("GrandparentView onInterceptTouchEvent  " + EventUtil.getEventName (ev.getAction ()));
        return super.onInterceptTouchEvent (ev);
    }


}
