package com.example.mybrary.ui.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;

import java.util.Date;
import java.util.List;

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

            Review newReview = new Review(wordId, 0, currentDate);
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
}
