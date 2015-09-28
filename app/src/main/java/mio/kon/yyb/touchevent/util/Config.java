package mio.kon.yyb.touchevent.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mio on 15-8-19.
 */
public class Config {

    private  final static String SDK_CONFIG_NAME = "mio-sdk-config.properties";
    private static Config mInstance;
    private boolean mIsDebug;
    private String mHostUrl;

    private Config(InputStream configIs) {
        Properties prop = new Properties ();
        try {
            prop.load (configIs);
            mIsDebug = Boolean.valueOf (prop.getProperty ("debug", "false"));
            mHostUrl = prop.getProperty ("host_url");

        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public boolean isDebug() {
        return mIsDebug;
    }

    public String getHostUrl() {
        return mHostUrl;
    }

    public synchronized static Config getInstance(Context context) {
        if (mInstance == null) {
            InputStream configIs = null;
            try {
                configIs = context.getAssets ().open (SDK_CONFIG_NAME);
                mInstance = new Config (configIs);
            } catch (IOException e) {
                e.printStackTrace ();
            } finally {
                try {
                    if (configIs != null) {
                        configIs.close ();
                    }
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }

        }
        return mInstance;
    }

}
