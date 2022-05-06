package com.example.mybrary.data.firebase;

import androidx.annotation.NonNull;

import com.example.mybrary.domain.model.Word;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDAO {

    private final DatabaseReference dbReference;

    public WordDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbReference = db.getReference().child("words").child(userId);
    }

    // Get all words
    public Query getWords() {
        return dbReference.orderByKey();
    }

    // Get word by id
    public Word getWordById() {
        // !!! CODE HERE !!!
        return null;
    }

    // Add word
    public void add(Word word) {
        dbReference.child(word.getId()).setValue(word);
    }

    // Update word
    public void update(String key, Word word) {
        dbReference.child(key).setValue(word);
    }

    // Update item(s) in word
    public void updateItem(String key, HashMap<String, Object> hashMap) {
        dbReference.child(key).updateChildren(hashMap);
    }

    // Delete word
    public void delete(String key) {
        dbReference.child(key).removeValue();
    }
}
