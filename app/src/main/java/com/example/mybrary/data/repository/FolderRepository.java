package com.example.mybrary.data.repository;

import androidx.room.Insert;
import com.example.mybrary.data.local.FolderEntity;
import com.example.mybrary.domain.model.Folder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;

public class FolderRepository {

    // Get All folders
    public List<Folder> getAllFolders() {

    }

    // Get Folder by ID
    public Folder getFolderById() {

    }

    // Add folder
    public Task<Void> add(Folder folder) {

    }

    // Update folder
    public Task<Void> update(String key, HashMap<String, Object> hashMap) {

    }

    // Delete folder
    public Task<Void> delete(String key) {

    }

}
