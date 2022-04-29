package com.example.mybrary.domain.util;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mybrary.data.repository.ReviewRepository;

public class UploadWorker extends Worker {

    ReviewRepository reviewRepo;

    public UploadWorker(Context context, WorkerParameters params){
        super(context, params);
        reviewRepo = new ReviewRepository((Application) context);
    }

    @NonNull
    @Override
    public Result doWork() {

        long wordId = getInputData().getLong("longVal", 0);
        reviewRepo.updateTimerById(false, wordId);
        System.out.println("Work Success!");

        // Indicate whether success
        return Result.success();
    }
}
