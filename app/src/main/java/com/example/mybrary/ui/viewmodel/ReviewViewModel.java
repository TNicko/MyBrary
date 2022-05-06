package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.domain.util.UploadWorker;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReviewViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;
    public LiveData<List<Word>> allWords;
    public LiveData<List<Review>> allReviews;


    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepo = new ReviewRepository(application);
        wordRepo = new WordRepository(application);
        allWords = wordRepo.getAllWords();
    }

    public void updateReview(String wordId, Date date, long level){
        Review review = new Review(wordId, level, date, true);
        reviewRepo.update(review);
    }

    public void setReviewCountdown(String wordId, long level){

        int newLevel = (int) level;
        int duration = levelToTimer(newLevel);
        TimeUnit timeUnit = levelToTimeUnit(newLevel);

        System.out.println("Work initiating...");
        System.out.println("Work Duration: "+duration + " "+timeUnit);
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInitialDelay(duration, timeUnit)
                .setInputData(new Data.Builder().putString("stringVal", wordId).build())
                .build();
        WorkManager.getInstance(getApplication()).enqueue(workRequest);
    }

    public TimeUnit levelToTimeUnit(int level) {

        if (level == 0) {
            return TimeUnit.SECONDS;
        }
        else if (level >= 1 && level <= 3) {
            return TimeUnit.MINUTES;
        }
        else if (level >= 4 && level <= 7) {
            return TimeUnit.HOURS;
        }
        else if (level >= 8) {
            return TimeUnit.DAYS;
        }
        else {
            return TimeUnit.SECONDS;
        }
    }

    public int levelToTimer(int level) {
        switch(level) {
            case 0:
                return 30; // seconds
            case 1:
                return 1; // minutes
            case 2:
                return 5; // minutes
            case 3:
                return 30; // minutes
            case 4:
                return 1; // hours
            case 5:
                return 3; // hours
            case 6:
                return 6; // hours
            case 7:
                return 12; // hours
            case 8:
                return 1; // day
            case 9:
                return 2; // day
            case 10:
                return 4; // day
            case 11:
                return 7; // day
            case 12:
                return 14; // day
            case 13:
                return 21; // day
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:{
                return 28; // day
            }
            default:
                System.out.println("ERROR (level to time conversion)");
                return 0;
        }
    }
}
