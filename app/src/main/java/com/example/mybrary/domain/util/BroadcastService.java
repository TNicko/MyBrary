package com.example.mybrary.domain.util;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.loader.content.AsyncTaskLoader;

import com.example.mybrary.data.repository.ReviewRepository;

public class BroadcastService extends Service {

    private final static String TAG = "BroadcastService";
    public static final String COUNTDOWN_BR = "com.example.mybrary";
    Intent bi = new Intent(COUNTDOWN_BR);
    private int duration = 0;
    CountDownTimer cdt = null;

    @Override
    public void onCreate() {
        super.onCreate();

        duration = 10000;
        Log.i(TAG, "Starting timer...");
        Log.i(TAG, "Duration: " + duration);
        cdt = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long duration) {
                Log.i(TAG, "Countdown seconds remaning: "+duration / 1000);
                bi.putExtra("countdown", duration);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
            }
        };
        cdt.start();

    }

    @Override
    public void onDestroy() {
        cdt.cancel();
        Log.i(TAG, "Time cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startTimer() {

    }

}
