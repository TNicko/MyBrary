package com.example.mybrary.ui.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.mybrary.R;
import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.ui.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kotlin.Triple;

public class MainViewModel extends AndroidViewModel {

    private FolderRepository folderRepo;
    private WordRepository wordRepo;
    private ReviewRepository reviewRepo;
    public LiveData<List<Folder>> folders;
    public LiveData<List<Word>> allWords;
    public LiveData<List<Review>> allReviews;
    public LiveData<Triple<List<Folder>, List<Word>, List<Review>>> allFolderInfo;


    public MainViewModel(@NonNull Application application) {
        super(application);
        folderRepo = new FolderRepository(application);
        wordRepo = new WordRepository(application);
        reviewRepo = new ReviewRepository(application);
        folders = folderRepo.getAllFolders();
        allWords = wordRepo.getAllWords();
        allReviews = reviewRepo.getAllReviews();
        // Combine live data
        allFolderInfo = new CombinedLiveData(folders, allWords, allReviews);
    }

    LiveData<List<Folder>> getAllFolders() {
        return folders;
    }

    public void addFolder(Folder folder) {
        folderRepo.add(folder);
    }

    public class CombinedLiveData extends MediatorLiveData<Triple<List<Folder>, List<Word>, List<Review>>> {
        private List<Folder> folders = Collections.emptyList();
        private List<Word> words = Collections.emptyList();
        private List<Review> reviews = Collections.emptyList();

        public CombinedLiveData(LiveData<List<Folder>> ld1, LiveData<List<Word>> ld2, LiveData<List<Review>> ld3) {
            postValue(new Triple<>(folders, words, reviews));

            addSource(ld1, folders -> {
                if(folders != null) {
                    this.folders = folders;
                }
                postValue(new Triple<>(folders, words, reviews));
            });
            addSource(ld2, words -> {
                if(words != null) {
                    this.words = words;
                }
                postValue(new Triple<>(folders, words, reviews));
            });
            addSource(ld3, reviews -> {
                if(reviews != null) {
                    this.reviews = reviews;
                }
                postValue(new Triple<>(folders, words, reviews));
            });
        }
    }

}
