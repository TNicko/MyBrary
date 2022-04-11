package com.example.mybrary.ui.viewmodel;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.ui.adapter.FolderRecViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainViewModel {

    private FolderRepository folderRepo;
    private WordRepository wordRepo;
    public List<Folder> folders = new ArrayList<>();
    public ArrayList<String> wordCounts = new ArrayList<>();

    public MainViewModel() {
        folderRepo = new FolderRepository();
        loadFolders();
        loadWordCounts();


    }

    public void loadFolders() {
        folders = folderRepo.getAllFolders();
    }

    // Get word count for each folder
    public void loadWordCounts() {
        for (Folder folder: folders) {
            wordRepo = new WordRepository(folder.getName());
            long wordCount = wordRepo.getAllWords().size();
            wordCounts.add(String.format(Locale.getDefault(), "%d", wordCount));
        }
    }


}
