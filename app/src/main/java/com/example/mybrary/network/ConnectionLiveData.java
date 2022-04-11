package com.example.mybrary.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;

import androidx.lifecycle.LiveData;

public class ConnectionLiveData extends LiveData<ConnectionModel> {

    private Context context;

    public ConnectionLiveData(Context context) {
        this.context = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        context.unregisterReceiver(networkReceiver);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                ConnectivityManager connManager = (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null
                        && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    switch (activeNetwork.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            postValue(new ConnectionModel("Wifi", true));
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            postValue(new ConnectionModel("Mobile", true));
                            break;
                    }
                } else {
                    postValue(new ConnectionModel(null, false));
                }
            }
        }
    };
}
