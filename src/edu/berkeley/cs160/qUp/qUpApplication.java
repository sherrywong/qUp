package edu.berkeley.cs160.qUp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 11/25/13.
 */
public class QUpApplication extends Application {

    public static Context s_applicationContext = null;
    private static String TAG = "QUpApplication";

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "configuration changed!");

    }

    /**
     * User to make Context globally aware.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        s_applicationContext = getApplicationContext();
        Log.d(TAG, "In onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "In on Terminate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "In onLowMemory");

    }

    public void registerQueueUpdateListener(BusinessListActivity businessListActivity) {
    }

    public void checkForUpdates() {

    }
}
