package com.example.mybrary.ui.viewmodel;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.domain.model.Folder;

public class NewFolderViewModel {

    private FolderRepository folderRepo;
    private String folderName;

    public NewFolderViewModel() {

    }

    public String checkFolderInput(String folderInput) {
        if (folderInput.equals("")) {
            return "null";
        }

        return "saved";
    }
}
