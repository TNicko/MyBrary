package com.example.mybrary.ui.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;

import java.util.Collections;
import java.util.List;

public class FolderViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;
    public LiveData<List<Word>> words;
    public LiveData<List<Review>> allReviews;
    public LiveData<Pair<List<Word>, List<Review>>> allWordInfo;


    public FolderViewModel(Application application, String folderId) {
        super(application);
        wordRepo = new WordRepository(application, folderId);
        words = wordRepo.getWords(folderId);
        reviewRepo = new ReviewRepository(application);
        allReviews = reviewRepo.getAllReviews();
        allWordInfo = new CombinedLiveData(words, allReviews);
    }

    LiveData<List<Word>> returnWords() {
        return words;
    }

    LiveData<List<Review>> returnAllReviews() { return allReviews;}

    public class CombinedLiveData extends MediatorLiveData<Pair<List<Word>, List<Review>>> {
        private List<Review> reviews = Collections.emptyList();
        private List<Word> words = Collections.emptyList();

        public CombinedLiveData(LiveData<List<Word>> ld1, LiveData<List<Review>> ld2) {
            postValue(Pair.create(words, reviews));

            addSource(ld1, words -> {
                if(words != null) {
                    this.words = words;
                }
                postValue(Pair.create(words, reviews));
            });
            addSource(ld2, reviews -> {
                if(reviews != null) {
                    this.reviews = reviews;
                }
                postValue(Pair.create(words, reviews));
            });
        }
    }

}
