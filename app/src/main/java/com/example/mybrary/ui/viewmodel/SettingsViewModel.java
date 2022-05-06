package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Word;

public class SettingsViewModel extends AndroidViewModel {

    FolderRepository folderRepo;
    WordRepository wordRepo;
    ReviewRepository reviewRepo;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        folderRepo = new FolderRepository(application);
        wordRepo = new WordRepository(application);
        reviewRepo = new ReviewRepository(application);
    }

    public void nukeDatabase() {
        folderRepo.nukeTable();
        wordRepo.nukeTable();
        reviewRepo.nukeTable();
    }
}
