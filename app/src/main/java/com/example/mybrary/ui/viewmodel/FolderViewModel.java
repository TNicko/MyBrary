package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;

import java.util.List;

public class FolderViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;
    public LiveData<List<Word>> words;
    public LiveData<List<Review>> reviews;

    public FolderViewModel(Application application, Long folderId) {
        super(application);
        wordRepo = new WordRepository(application, folderId);
        words = wordRepo.getWords(folderId);

        reviewRepo = new ReviewRepository(application);
        reviews = reviewRepo.getAllReviews();
    }

    LiveData<List<Word>> getWords() {
        return words;
    }

    LiveData<List<Review>> getAllReviews() { return reviews;}

}
