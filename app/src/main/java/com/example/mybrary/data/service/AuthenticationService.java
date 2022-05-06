package com.example.mybrary.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * A bound Service that instantiates the authenticator when started.
 */
public class AuthenticationService extends Service {

private StubAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new StubAuthenticator(this);
    }

    // When the system binds to this Service to make the RPC call return the authenticator's IBinder.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
