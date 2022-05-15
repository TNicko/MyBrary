package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.domain.model.Folder;

import java.util.UUID;

public class NewFolderViewModel extends AndroidViewModel {

    private FolderRepository folderRepo;
    private String folderName;
//    private LiveData<List<Folder>> folders;

    public NewFolderViewModel(Application application) {
        super(application);
        folderRepo = new FolderRepository(application);
    }

    public String checkFolderInput(String folderInput) {

        if (folderInput.equals("")) {
            return "null";
        } else {
            String uui = UUID.randomUUID().toString();
            Folder newFolder = new Folder(uui, folderInput);
            folderRepo.add(newFolder);
            return "saved";
        }
    }
}
