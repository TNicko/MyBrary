package com.example.mybrary.data.repository;

import com.example.mybrary.data.firebase.FolderDAO;
import com.example.mybrary.data.local.FolderLocalDAO;
import com.example.mybrary.domain.model.Folder;

import java.util.HashMap;
import java.util.List;

public class FolderRepository {

    FolderDAO remoteDao;
    FolderLocalDAO localDao;

    public FolderRepository() {
        remoteDao = new FolderDAO();
    }

    // Get All folders
    public List<Folder> getAllFolders() {
        System.out.println("FOLDERS: "+remoteDao.getAllFolders());
        return remoteDao.getAllFolders();
    }

    // Get Folder by ID
    public Folder getFolderById() {
        return remoteDao.getFolderById();
    }

    // Add folder
    public void add(Folder folder) {
        remoteDao.add(folder);
    }

    // Update folder
    public void update(String key, HashMap<String, Object> hashMap) {
        remoteDao.update(key, hashMap);

    }

    // Delete folder
    public void delete(String key) {
        remoteDao.delete(key);
    }

}
