package com.example.OnlineDio.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 11/1/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class OnlineSyncService extends Service
{
    private static final Object sSyncAdapterLock = new Object();
    private static OnlineSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate()
    {
        synchronized (sSyncAdapterLock)
        {
            if (sSyncAdapter == null)
            {
                sSyncAdapter = new OnlineSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
