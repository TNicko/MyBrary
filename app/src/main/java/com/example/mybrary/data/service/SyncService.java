package com.example.mybrary.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SyncService extends Service {

    private static SyncAdapter sSyncAdapter = null;
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {

        // Create the sync adapter as a singleton.Set the sync adapter as syncable. Disallow parallel syncs
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    // Return an object that allows the system to invoke the sync adapter.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        /*
         * Get the object that allows external processes to call onPerformSync().
         * The object is created in the base class code when the SyncAdapter constructors call super().
         */
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
