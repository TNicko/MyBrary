package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    private MutableLiveData<List<Word>> word = new MutableLiveData<>();

    public WordRepository(Application application, Long folderId) {
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.wordDao();
        wordMapper = new WordDataMapper();
        readWords = wordMapper.fromLiveEntityList(localDao.getAll(folderId));
    }

    public WordRepository(Application application) {
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.wordDao();
    }

    private final boolean isLocal = true;

    // Get all words
    public LiveData<List<Word>> getAllWords(Long folderId) {
        if (isLocal) {
            return readWords;
        } else {
            return null;
        }
    }

    // Get word by id
    public void getWordById(Long id) {
        if (isLocal) {
            String wordId = id.toString();
            QueryAsyncTask task = new QueryAsyncTask(localDao);
            task.delegate = this;
            task.execute(wordId);
        } else {

        }
    }

    // Return word
    public MutableLiveData<List<Word>> returnWord() {
        return word;
    }

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
    public void update(Word word) {
        if (isLocal) {
            WordRepository.UpdateAsyncTask task = new WordRepository.UpdateAsyncTask(localDao);
            task.execute(word);
        } else {
//            remoteDao.update(word);
        }
    }

    // Delete word
    public void delete(String key) {
        remoteDao.delete(key);
    }

    private void asyncFinished(List<Word> results) {
        word.setValue(results);
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void, List<Word>> {

        private WordLocalDAO asyncTaskDao;
        private WordRepository delegate = null;
        private WordDataMapper wordMapper = new WordDataMapper();

        QueryAsyncTask(WordLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Word> doInBackground(String... params) {
            return wordMapper.fromEntityList(asyncTaskDao.getById(params[0]));
        }

        @Override
        protected void onPostExecute(List<Word> result) {
            delegate.asyncFinished(result);
        }
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

    private static class UpdateAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordLocalDAO asyncTaskDao;
        private final WordDataMapper wordMapper = new WordDataMapper();

        UpdateAsyncTask(WordLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            System.out.println(words[0]);
            WordEntity wordEntity = wordMapper.mapToEntity(words[0]);
            asyncTaskDao.update(wordEntity);
            return null;
        }
    }

}
