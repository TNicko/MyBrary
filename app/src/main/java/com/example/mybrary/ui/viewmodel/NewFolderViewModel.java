package com.example.mybrary.ui.viewmodel;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.domain.model.Folder;

import java.util.ArrayList;
import java.util.List;

public class NewFolderViewModel {

    private FolderRepository folderRepo;
    private String folderName;
    private List<Folder> folders = new ArrayList<>();

    public NewFolderViewModel() {
        folderRepo = new FolderRepository();
        folders = folderRepo.getAllFolders();
    }

    public String checkFolderInput(String folderInput) {
        if (folderInput.equals("")) {
            return "null";
        }

        boolean folderExists = folders.stream().anyMatch(o -> o.getName().equals(folderInput));
        if (folderExists) {
            return "exists";
        } else {
            Folder newFolder = new Folder(folderInput);
            folderRepo.add(newFolder);
            return "saved";
        }
    }
}
