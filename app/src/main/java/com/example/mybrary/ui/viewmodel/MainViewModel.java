package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private FolderRepository folderRepo;
    private WordRepository wordRepo;
    public LiveData<List<Folder>> folders;
    private ArrayList<String> wordCounts = new ArrayList<>();



    public MainViewModel(@NonNull Application application) {
        super(application);
        folderRepo = new FolderRepository(application);
        folders = folderRepo.getAllFolders();
    }

    LiveData<List<Folder>> getAllFolders() {
        return folders;
    }

    public void addFolder(Folder folder) {
        folderRepo.add(folder);
    }

    // Get word count for each folder
//    public void loadWordCounts() {
//        for (Folder folder: folders) {
//            System.out.println("Word count: ");
//            wordRepo = new WordRepository(folder.getName());
//            long wordCount = wordRepo.getAllWords().size();
//            wordCounts.add(String.format(Locale.getDefault(), "%d", wordCount));
//        }
//    }


}
