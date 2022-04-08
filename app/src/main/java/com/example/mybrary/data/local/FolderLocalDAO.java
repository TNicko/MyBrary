package com.example.mybrary.data.local;

import com.example.mybrary.data.repository.FolderRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class FolderLocalDAO {

    // Get all folders
    public Query getAll() {
        return null;
    }

    // Add folder
    public Task<Void> add(Folder folder) {
        return null;
    }

    // Update folder
    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return null;
    }

    // Remove folder
    public Task<Void> remove(String key) {
        return null;
    }
}
