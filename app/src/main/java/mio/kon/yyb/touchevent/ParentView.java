package mio.kon.yyb.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import mio.kon.yyb.touchevent.util.L;

/**
 * Created by mio on 15/9/24.
 */
public class ParentView extends ViewGroup {


    public ParentView(Context context) {
        this (context, null);

    }


    public ParentView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init ();
    }

    private void init() {
        setBackgroundColor (getResources ().getColor (R.color.color2));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt (0);
        LayoutParams st = child.getLayoutParams ();
        child.layout (0, 0, st.width, st.height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.d ("ParentView onTouchEvent  " + EventUtil.getEventName (event.getAction ()));
//        return true;
        return super.onTouchEvent (event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.d ("ParentView dispatchTouchEvent  " + EventUtil.getEventName (ev.getAction ()));
        return true;
//        return super.dispatchTouchEvent (ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        L.d ("ParentView onInterceptTouchEvent  " + EventUtil.getEventName (ev.getAction ()));
//        return true;
        return super.onInterceptTouchEvent (ev);
    }


}
