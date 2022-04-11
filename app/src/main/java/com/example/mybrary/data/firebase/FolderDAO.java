package com.example.mybrary.data.firebase;

import androidx.annotation.NonNull;

import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.domain.model.Folder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FolderDAO{

    private final DatabaseReference dbReference;

    public FolderDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference("Folder");
    }

    // Get all folders
    public List<Folder> getAllFolders() {
        System.out.println("getting folders...");
        List<Folder> folders = new ArrayList<>();
        dbReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    System.out.println(data);

                    Map<String,Object> td = (HashMap<String, Object>)data.getValue();
                    Folder folder = new Folder(td.get("name").toString());
                    folders.add(folder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return folders;
    }

    // Get folder by id
    public Folder getFolderById() {
        // !!! CODE HERE !!!
        return null;
    }

    // Add folder
    public Task<Void> add(Folder folder) {
        return dbReference.push().setValue(folder);
    }

    // Delete folder
    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }

    // Update folder
    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return dbReference.child(key).updateChildren(hashMap);

    }

}
