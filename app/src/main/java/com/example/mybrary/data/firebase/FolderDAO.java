package com.example.mybrary.data.firebase;

import com.example.mybrary.data.repository.FolderRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class FolderDAO implements FolderRepository {

    private final DatabaseReference dbReference;

    public FolderDAO() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference(Folder.class.getSimpleName());
    }

    @Override
    public Query get() {
        return dbReference.orderByKey();
    }

    @Override
    public Task<Void> add(Folder folder) {
        return dbReference.push().setValue(folder);
    }

    @Override
    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return dbReference.child(key).updateChildren(hashMap);
    }

    @Override
    public Task<Void> remove(String key) {
        return dbReference.child(key).removeValue();
    }
}
