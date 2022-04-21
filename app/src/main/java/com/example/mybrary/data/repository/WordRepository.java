package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.mybrary.data.firebase.WordDAO;
import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.local.dao.WordLocalDAO;
import com.example.mybrary.data.local.dataMapper.WordDataMapper;
import com.example.mybrary.data.local.entity.WordEntity;
import com.example.mybrary.domain.model.Word;

import java.util.HashMap;
import java.util.List;

public class WordRepository {

    private WordDAO remoteDao;
    private WordLocalDAO localDao;
    private WordDataMapper wordMapper;
    private LiveData<List<Word>> readWords;

    public WordRepository(Application application) {
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.wordDao();
        wordMapper = new WordDataMapper();
        readWords = wordMapper.fromLiveEntityList(localDao.getAll());
    }

    private final boolean isLocal = true;

    // Get all words
    public LiveData<List<Word>> getAllWords() {
        if (isLocal) {
            return readWords;
        } else {
            return null;
        }
    }

    //Get word by id
//    public Word getWordById() {
//        return remoteDao.getWordById();
//    }

    // Add word
    public void add(Word word) {
        if (isLocal) {
            WordRepository.InsertAsyncTask task = new WordRepository.InsertAsyncTask(localDao);
            task.execute(word);
        } else {
            remoteDao.add(word);
        }
    }

    // Update word
    public void update(String key, HashMap<String, Object> hashMap) {
        if (isLocal) {
            localDao.update();
        } else {
            remoteDao.update(key, hashMap);
        }
    }

    // Delete word
    public void delete(String key) {
        remoteDao.delete(key);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordLocalDAO asyncTaskDao;
        private final WordDataMapper wordMapper = new WordDataMapper();

        InsertAsyncTask(WordLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            System.out.println(words[0]);
            WordEntity wordEntity = wordMapper.mapToEntity(words[0]);
            asyncTaskDao.add(wordEntity);
            return null;
        }
    }

}
