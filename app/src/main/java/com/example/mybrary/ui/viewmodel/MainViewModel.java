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
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.ui.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private FolderRepository folderRepo;
    private WordRepository wordRepo;
    public LiveData<List<Folder>> folders;
    public LiveData<List<Word>> allWords;
    public LiveData<Pair<List<Folder>, List<Word>>> allFolderInfo;


    public MainViewModel(@NonNull Application application) {
        super(application);
        folderRepo = new FolderRepository(application);
        wordRepo = new WordRepository(application);
        folders = folderRepo.getAllFolders();
        allWords = wordRepo.getAllWords();
        allFolderInfo = new CombinedLiveData(folders, allWords);
    }

    LiveData<List<Folder>> getAllFolders() {
        return folders;
    }

    public void addFolder(Folder folder) {
        folderRepo.add(folder);
    }

    public class CombinedLiveData extends MediatorLiveData<Pair<List<Folder>, List<Word>>> {
        private List<Folder> folders = Collections.emptyList();
        private List<Word> words = Collections.emptyList();

        public CombinedLiveData(LiveData<List<Folder>> ld1, LiveData<List<Word>> ld2) {
            postValue(Pair.create(folders, words));

            addSource(ld1, folders -> {
                if(folders != null) {
                    this.folders = folders;
                }
                postValue(Pair.create(folders, words));
            });
            addSource(ld2, words -> {
                if(words != null) {
                    this.words = words;
                }
                postValue(Pair.create(folders, words));
            });
        }
    }

}
