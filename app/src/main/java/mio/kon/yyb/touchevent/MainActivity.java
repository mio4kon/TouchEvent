package mio.kon.yyb.touchevent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import mio.kon.yyb.touchevent.util.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.d ("Activity onTouchEvent "+EventUtil.getEventName (event.getAction ()));
        return super.onTouchEvent (event);
    }
}
