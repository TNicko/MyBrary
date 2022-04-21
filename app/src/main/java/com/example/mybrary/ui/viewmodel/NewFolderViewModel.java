package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.domain.model.Folder;

public class NewFolderViewModel extends AndroidViewModel {

    private FolderRepository folderRepo;
    private String folderName;
//    private LiveData<List<Folder>> folders;

    public NewFolderViewModel(Application application) {
        super(application);
        folderRepo = new FolderRepository(application);
    }

    public String checkFolderInput(String folderInput) {

        Folder newFolder = new Folder(0, folderInput);
        folderRepo.add(newFolder);
        return "saved";

//        if (folderInput.equals("")) {
//            return "null";
//        }
//
//        boolean folderExists = folders.stream().anyMatch(o -> o.getName().equals(folderInput));
//        if (folderExists) {
//            return "exists";
//        } else {
//
//        }
    }
}
