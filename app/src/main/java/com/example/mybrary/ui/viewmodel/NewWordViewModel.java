package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NewWordViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;

    public NewWordViewModel(Application application) {
        super(application);
        wordRepo = new WordRepository(application);
        reviewRepo = new ReviewRepository(application);
    }

    public void addWord(Word word) {
        wordRepo.add(word);
    }

    public void checkReview(Boolean isReview, String wordId) {
        if (isReview){
            Date currentDate = new Date();
            System.out.println(wordId +" = word id");
            Review newReview = new Review(wordId, 0, currentDate, true);
            setCountdown(wordId);
            System.out.println("level: " + newReview.getLevel());
            reviewRepo.add(newReview);

        }
    }


    public String checkWordInput(String word, String translation, String notes, Boolean review, String folderId) {

        if (word.equals("")) {
            return "word null";
        } else if (translation.equals("")) {
            return "translation null";
        } else {
            String uuid = UUID.randomUUID().toString();
            Word newWord = new Word(uuid, folderId, word, translation, notes, review);
            addWord(newWord);
            System.out.println("word added");
            System.out.println("word Id being saved in review = "+uuid);
            checkReview(review, uuid);
            return "saved";
        }
    }

    public void setCountdown(String wordId) {
        System.out.println("Work initiating...");
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInitialDelay(30, TimeUnit.SECONDS)
                .setInputData(new Data.Builder().putString("stringVal", wordId).build())
                .build();

        WorkManager.getInstance(getApplication()).enqueue(workRequest);
    }
}
