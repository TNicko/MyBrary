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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UpdateWordViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;
    public MutableLiveData<List<Word>> word;
    public MutableLiveData<List<Review>> review;
    private Long wordId;

    public UpdateWordViewModel(Application application, Long id) {
        super(application);
        wordId = id;
        wordRepo = new WordRepository(application);
        reviewRepo = new ReviewRepository(application);
        getWord(id);
        getReview(id);
        word = wordRepo.returnWord();
        review = reviewRepo.returnReview();
    }

    public MutableLiveData<List<Word>> returnWord() {
        return word;
    }

    public void deleteWord() {
        wordRepo.delete(word.getValue().get(0));
    }

    public void deleteReview() {
        if (review.getValue().size() > 0){
            reviewRepo.delete(review.getValue().get(0));
            System.out.println("Review deleted");
        }
    }

    public void updateWord(Word word) {
        wordRepo.update(word);
    }

    public void getWord(Long wordId){
        wordRepo.getWordById(wordId);
    }

    public void getReview(Long wordId) {
        reviewRepo.getReviewById(wordId);
    }

    public String checkWordInput(long wordId, long folderId, String wordName, String translation, String notes, boolean review, boolean oldReview) {

        if (wordName.equals("")) {
            return "word null";
        } else if (translation.equals("")) {
            return "translation null";
        } else {
            Word word = new Word(wordId, folderId, wordName, translation, notes, review);
            wordRepo.update(word);

            //check/update review
            checkReview(word, oldReview);

            return "saved";
        }
    }

    public void checkReview(Word word, Boolean oldReview){
        // Turned on review (add review)
        if (word.isReview() && !oldReview) {
            System.out.println("now reviewable");
            Date currentDate = new Date();
            Review newReview = new Review(word.getId(), 0, currentDate, true);
            reviewRepo.add(newReview);

            System.out.println("Work initiating...");
            WorkRequest workRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                    .setInitialDelay(30, TimeUnit.SECONDS)
                    .setInputData(new Data.Builder().putLong("longVal", word.getId()).build())
                    .build();
            WorkManager.getInstance(getApplication()).enqueue(workRequest);
        }
        // Turned off review (delete review)
        else if (!word.isReview() && oldReview) {
            System.out.println("now not reviewable");
            deleteReview();
        }
    }
}
