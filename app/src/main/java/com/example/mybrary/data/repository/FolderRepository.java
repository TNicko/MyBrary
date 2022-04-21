package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mybrary.data.firebase.FolderDAO;
import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.local.dataMapper.FolderDataMapper;
import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.dao.FolderLocalDAO;
import com.example.mybrary.domain.model.Folder;

import java.util.HashMap;
import java.util.List;

public class FolderRepository {

    private FolderDAO remoteDao;
    private FolderLocalDAO localDao;
    private FolderDataMapper folderMapper;
    private LiveData<List<Folder>> readFolders;

    public FolderRepository(Application application) {
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.folderDao();
        folderMapper = new FolderDataMapper();
        readFolders = folderMapper.fromLiveEntityList(localDao.getAll());
    }


    // Dummy boolean
    private final boolean isLocal = true;

    // Get All folders
    public LiveData<List<Folder>> getAllFolders() {
        if (isLocal) {
            return readFolders;
        } else {
            return null;
        }
    }

//    // Get Folder by ID
//    public Folder getFolderById() {
//        if (isLocal) {
//            return null;
//        } else {
//            return remoteDao.getFolderById();
//        }
//    }

    // Add folder
    public void add(Folder folder) {
        if (isLocal) {
            InsertAsyncTask task = new InsertAsyncTask(localDao);
            task.execute(folder);
        } else {
            remoteDao.add(folder);
        }
    }

    // Update folder
    public void update(String key, HashMap<String, Object> hashMap) {
        if (isLocal) {
            localDao.update();
        } else {
            remoteDao.update(key, hashMap);
        }


    }

    // Delete folder
    public void delete(String key) {
        remoteDao.delete(key);
    }

    private static class InsertAsyncTask extends AsyncTask<Folder, Void, Void> {

        private FolderLocalDAO asyncTaskDao;
        private final FolderDataMapper folderMapper = new FolderDataMapper();

        InsertAsyncTask(FolderLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Folder... folder) {
            System.out.println(folder[0]);
            FolderEntity folderEntity = folderMapper.mapToEntity(folder[0]);
            asyncTaskDao.add(folderEntity);
            return null;
        }
    }


//    private static class QueryAsyncTask extends
//            AsyncTask<String, Void, List<Folder>> {
//
//        private FolderLocalDAO asyncTaskDao;
//        private FolderRepository delegate = null;
//
//        QueryAsyncTask(FolderLocalDAO dao) {
//            asyncTaskDao = dao;
//        }
//
//        @Override
//        protected List<Folder> doInBackground(String... strings) {
//            return asyncTaskDao.getAll();
//        }
//
//        @Override
//        protected void onPostExecute(List<Folder> result) {
//            delegate.asyncFinished(result);
//        }
//    }

}
