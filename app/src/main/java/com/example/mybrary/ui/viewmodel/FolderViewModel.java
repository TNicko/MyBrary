package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Word;

import java.util.List;

public class FolderViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    public LiveData<List<Word>> words;

    public FolderViewModel(Application application, Long folderId) {
        super(application);
        wordRepo = new WordRepository(application, folderId);
        words = wordRepo.getAllWords(folderId);
    }

    LiveData<List<Word>> getAllWords() {
        return words;
    }

}
