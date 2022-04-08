package com.example.mybrary.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.HashMap;

public interface FolderRepository {

    // Get Folder
    public Query get();

    // Add Folder
    public Task<Void> add(Folder folder);

    // Edit Folder
    public Task<Void> update(String key, HashMap<String, Object> hashMap);

    // Remove Folder
    public Task<Void> remove(String key);

}
