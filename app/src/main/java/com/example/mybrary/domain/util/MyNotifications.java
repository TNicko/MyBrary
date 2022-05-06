package com.example.mybrary.domain.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mybrary.R;
import com.example.mybrary.ui.view.MainActivity;

// Class for creating and managing app notifications
public class MyNotifications {

    private Context mContext;
    private String cId;

    public MyNotifications(Context context, String channelId, String channelName) {
        createNotificationChannel(context, channelId, channelName);
        mContext = context;
        cId = channelId;
    }

    private void createNotificationChannel(Context context, String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    // notification displayed when review wait time is over
    public void newReviewNotification() {

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, cId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("MyBrary")
                .setContentText("New review available!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(mContext);
        managerCompat.notify(1, builder.build());
    }



}
