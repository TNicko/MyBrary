package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mybrary.data.firebase.dao.FolderDAO;
import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.dataMapper.FolderDataMapper;
import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.dao.FolderLocalDAO;
import com.example.mybrary.domain.model.Folder;

import java.util.List;

public class FolderRepository {

    private FolderDAO remoteDao;
    private FolderLocalDAO localDao;
    private FolderDataMapper folderMapper;
    private LiveData<List<Folder>> readFolders;

    public FolderRepository(Application application) {

        // local database
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.folderDao();
        folderMapper = new FolderDataMapper();
        readFolders = folderMapper.fromLiveEntityList(localDao.getAll());

    }

    // Return All folders
    public LiveData<List<Folder>> getAllFolders() {
        return readFolders;
    }

    // Add folder
    public void add(Folder folder) {
        // -> local database
        InsertAsyncTask task = new InsertAsyncTask(localDao);
        task.execute(folder);

        // -> remote database
        remoteDao = new FolderDAO();
        remoteDao.add(folder);
    }

    // Update folder
    public void update(Folder folder) {
        // -> local database
        localDao.update();

        // -> remote database
        remoteDao = new FolderDAO();
        remoteDao.update(folder.getId(), folder);
    }

    // Delete folder
    public void delete(Folder folder) {
        // -> local database
        FolderRepository.DeleteAsyncTask task = new FolderRepository.DeleteAsyncTask(localDao);
        task.execute(folder);

        // ->remote database
        remoteDao = new FolderDAO();
        remoteDao.delete(folder.getId());
    }

    // Add folder locally on login
    public void addOnLogin(Folder folder) {
        InsertAsyncTask task = new InsertAsyncTask(localDao);
        task.execute(folder);
    }

    // Delete all data
    public void nukeTable() {
        localDao.nukeTable();
    }

    private static class DeleteAsyncTask extends AsyncTask<Folder, Void, Void> {

        private FolderLocalDAO asyncTaskDao;
        private final FolderDataMapper folderMapper = new FolderDataMapper();

        DeleteAsyncTask(FolderLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Folder... params) {
            FolderEntity folderEntity = folderMapper.mapToEntity(params[0]);
            asyncTaskDao.delete(folderEntity);
            return null;
        }
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


}
