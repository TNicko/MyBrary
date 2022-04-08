package com.example.mybrary.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.HashMap;

public interface FolderRepository {

    public Object getAllFolders();

    public void addFolder(Folder folder);

    public void updateFolder(String key, HashMap<String, Object> hashMap);

    public void removeFolder(String key);

}
