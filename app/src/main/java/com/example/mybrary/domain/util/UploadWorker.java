package com.example.mybrary.domain.util;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mybrary.R;
import com.example.mybrary.data.repository.ReviewRepository;

public class UploadWorker extends Worker {

    ReviewRepository reviewRepo;
    Context mContext;
    MyNotifications notification;

    public UploadWorker(Context context, WorkerParameters params){
        super(context, params);
        mContext = context;
        reviewRepo = new ReviewRepository((Application) context);
        notification = new MyNotifications(context, "reviewChannel", "Review Channel");
    }

    @NonNull
    @Override
    public Result doWork() {

        String wordId = getInputData().getString("stringVal");
        reviewRepo.updateTimerById(false, wordId);

        // Create notification
        notification.newReviewNotification();

        // Indicate whether success
        return Result.success();
    }
}
