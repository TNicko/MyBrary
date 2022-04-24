package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;

import java.util.Date;
import java.util.List;

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
        reviewRepo.delete(review.getValue().get(0));
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

    public String checkWordInput(String word, String translation, String notes, Boolean review, Long folderId) {

        if (word.equals("")) {
            return "word null";
        } else if (translation.equals("")) {
            return "translation null";
        } else {
            Word newWord = new Word(0, folderId, word, translation, notes, review);
            wordRepo.add(newWord);
            return "saved";
        }

    }

    public void checkReview(Word word, Boolean oldReview){
        // Turned on review (add review)
        if (word.isReview() && !oldReview) {
            System.out.println("now reviewable");
            Date currentDate = new Date();
            Review newReview = new Review(word.getId(), 0, currentDate);
            reviewRepo.add(newReview);
        }
        // Turned off review (delete review)
        else if (!word.isReview() && oldReview) {
            System.out.println("now not reviewable");
            deleteReview();
        }
    }
}
