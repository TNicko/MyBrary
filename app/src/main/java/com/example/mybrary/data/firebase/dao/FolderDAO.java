package com.example.mybrary.data.firebase.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mybrary.domain.model.Folder;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FolderDAO{

    private final DatabaseReference dbReference;

    public FolderDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbReference = db.getReference().child("folders").child(userId);
    }

    // Get all folders
    public Query getAllFolders() {
        return dbReference.orderByKey();
    }

    // Get folder by id
    public Folder getFolderById() {
        // !!! CODE HERE !!!
        return null;
    }

    // Add folder
    public void add(Folder folder) {
        dbReference.child(folder.getId()).setValue(folder);
    }

    // Delete folder
    public void delete(String key) {
        dbReference.child(key).removeValue();
    }

    // Update folder
    public void update(String key, Folder folder) {
        dbReference.child(key).setValue(folder);
    }

    // update item(s) in folder
    public void updateItem(String key, HashMap<String, Object> hashMap) {
        dbReference.child(key).updateChildren(hashMap);
    }

}

