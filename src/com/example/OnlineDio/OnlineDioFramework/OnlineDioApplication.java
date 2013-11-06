package com.example.OnlineDio.OnlineDioFramework;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 10/31/13
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class OnlineDioApplication extends Application
{
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate()
    {
        // First, call the parent class
        super.onCreate();

        // This is a place to put code that must manage storage across
        // multiple activities, but it's better to keep most things in a
        // database, rather than in memory
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onTerminate()
    {
        Log.i(TAG, "onTerminate");

    }

    @Override
    public void onLowMemory()
    {
        // In-memory caches should be thrown overboard here
        Log.i(TAG, "onLowMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        Log.i(TAG, "onConifgurationChanged");
        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, newConfig.toString());
        }
    }

}
