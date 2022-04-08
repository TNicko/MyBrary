package com.example.mybrary.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.HashMap;

public interface WordRepository {

    // Get Word
    public Query get();

    // Add Word
    public Task<Void> add(Word word);

    // Edit Word
    public Task<Void> update(String key, HashMap<String, Object> hashMap);

    // Remove Word
    public Task<Void> remove(String key);

}
