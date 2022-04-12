package com.example.mybrary.ui.viewmodel;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.ui.adapter.FolderRecViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainViewModel {

    private FolderRepository folderRepo;
    private WordRepository wordRepo;
    public List<Folder> folders = new ArrayList<>();
    private ArrayList<String> wordCounts = new ArrayList<>();

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
            System.out.println("Word count: ");
            wordRepo = new WordRepository(folder.getName());
            long wordCount = wordRepo.getAllWords().size();
            wordCounts.add(String.format(Locale.getDefault(), "%d", wordCount));
        }
    }


}
