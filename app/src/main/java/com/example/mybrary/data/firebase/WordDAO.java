package com.example.mybrary.data.firebase;

import androidx.annotation.NonNull;

import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Word;
import com.google.android.gms.tasks.Task;
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

    public WordDAO(String folderName) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference(Word.class.getSimpleName()).child(folderName);
    }

    // Get all words
    public List<Word> getAllWords() {
        ArrayList<Word> words = new ArrayList<>();

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Map<String,Object> td=(HashMap<String, Object>)data.getValue();
                    long status = Long.parseLong(td.get("status_level").toString());
                    System.out.println(td.get("status_level"));
                    Word word = new Word(td.get("word").toString(),
                            td.get("translation").toString(),
                            td.get("notes").toString(),
                            data.getKey());
                    words.add(word);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return words;
    }

    // Get word by id
    public Word getWordById() {
        // !!! CODE HERE !!!
        return null;
    }

    // Add word
    public Task<Void> add(Word word) {
        return dbReference.push().setValue(word);
    }

    // Update word
    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return dbReference.child(key).updateChildren(hashMap);
    }

    // Delete word
    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }
}
