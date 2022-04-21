package com.example.mybrary.data.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mybrary.domain.model.Folder;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    ExecutorService execService = Executors.newSingleThreadExecutor();
    ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(execService);

    public FolderDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference("Folder");
    }

    // Get all folders
    public List<Folder> getAllFolders() {

        ListenableFuture<List<Folder>> asyncTask = lExecService.submit(
                new Callable<List<Folder>>() {
                    @Override
                    public List<Folder> call() throws Exception {
                        List<Folder> folders = new ArrayList<>();
                        dbReference.orderByKey().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {

                                    Map<String,Object> td = (HashMap<String, Object>)data.getValue();
                                    Folder folder = new Folder((Long) td.get("id"), td.get("name").toString());
                                    folders.add(folder);
                                }
                                System.out.println(folders);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        return folders;
                    }
                }
        );
        List<Folder> folders = new ArrayList<>();

        Futures.addCallback(
                asyncTask,
                new FutureCallback<List<Folder>>() {
                    @Override
                    public void onSuccess(List<Folder> result) {
                        System.out.println("Callback result: "+result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("Future Callback (folder list) failed!");
                    }
                },
                lExecService
        );
        return folders;
    }

    // Get folder by id
    public Folder getFolderById() {
        // !!! CODE HERE !!!
        return null;
    }

    // Add folder
    public void add(Folder folder) {
        dbReference.push().setValue(folder);
    }

    // Delete folder
    public void delete(String key) {
        dbReference.child(key).removeValue();
    }

    // Update folder
    public void update(String key, HashMap<String, Object> hashMap) {
        dbReference.child(key).updateChildren(hashMap);

    }

}

