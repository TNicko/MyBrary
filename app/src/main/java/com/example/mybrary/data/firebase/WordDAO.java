package com.example.mybrary.data.firebase;

import com.example.mybrary.data.repository.WordRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class WordDAO implements WordRepository {

    private final DatabaseReference dbReference;

    public WordDAO(String folderName) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference(Word.class.getSimpleName()).child(folderName);
    }

    // Get Words
    @Override
    public Query get() {
        return dbReference.orderByKey();
    }

    // Add Word
    @Override
    public Task<Void> add(Word word) {
        return dbReference.push().setValue(word);
    }

    // Update Word
    @Override
    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return dbReference.child(key).updateChildren(hashMap);
    }

    // Remove Word
    @Override
    public Task<Void> remove(String key) {
        return dbReference.child(key).removeValue();
    }
}
