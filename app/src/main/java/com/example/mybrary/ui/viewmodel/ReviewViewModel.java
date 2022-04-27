package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;
    public LiveData<List<Word>> allWords;
    public LiveData<List<Review>> allReviews;


    public ReviewViewModel(@NonNull Application application) {
        super(application);
        wordRepo = new WordRepository(application);
        allWords = wordRepo.getAllWords();
    }

}
