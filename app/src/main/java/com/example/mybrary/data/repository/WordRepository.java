package com.example.mybrary.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.HashMap;

public interface WordRepository {

    // Get all words
    public Query getAll();

    // Add word
    public Task<Void> add(Word word);

    // Edit word
    public Task<Void> update(String key, HashMap<String, Object> hashMap);

    // Remove word
    public Task<Void> remove(String key);

}
