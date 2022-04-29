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
import java.util.concurrent.TimeUnit;

public class NewWordViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;
    public MutableLiveData<Long> wordId;

    public NewWordViewModel(Application application) {
        super(application);
        wordRepo = new WordRepository(application);
        reviewRepo = new ReviewRepository(application);
        wordId = wordRepo.returnWordId();
    }

    public void addWord(Word word) {
        wordRepo.add(word);
    }

    public MutableLiveData<Long> returnWordId() {
        return wordId;
    }

    public void checkReview(Boolean isReview, long wordId) {
        if (isReview){
            Date currentDate = new Date();
            System.out.println(wordId +" = word id");

            Review newReview = new Review(wordId, 0, currentDate, true);
            System.out.println("level: " + newReview.getLevel());
            reviewRepo.add(newReview);

        }
    }

    public String checkWordInput(String word, String translation, String notes, Boolean review, Long folderId) {

        if (word.equals("")) {
            return "word null";
        } else if (translation.equals("")) {
            return "translation null";
        } else {
            Word newWord = new Word(0, folderId, word, translation, notes, review);
            addWord(newWord);
            System.out.println("word added");
            return "saved";
        }
    }

    public void setCountdown(long wordId) {
        System.out.println("Work initiating...");
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInitialDelay(30, TimeUnit.SECONDS)
                .setInputData(new Data.Builder().putLong("longVal", wordId).build())
                .build();

        WorkManager.getInstance(getApplication()).enqueue(workRequest);
    }
}
