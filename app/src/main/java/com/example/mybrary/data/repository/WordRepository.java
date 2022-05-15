package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mybrary.data.firebase.dao.WordDAO;
import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.local.dao.WordLocalDAO;
import com.example.mybrary.data.dataMapper.WordDataMapper;
import com.example.mybrary.data.local.entity.WordEntity;
import com.example.mybrary.domain.model.Word;

import java.util.List;

public class WordRepository {

    private WordDAO remoteDao;
    private WordLocalDAO localDao;
    private WordDataMapper wordMapper;
    private LiveData<List<Word>> readWords;
    private LiveData<List<Word>> readAllWords;
    private MutableLiveData<List<Word>> word = new MutableLiveData<>();

    public WordRepository(Application application, String folderId) {
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.wordDao();
        wordMapper = new WordDataMapper();
        readWords = wordMapper.fromLiveEntityList(localDao.getAllByFolder(folderId));
        readAllWords = wordMapper.fromLiveEntityList(localDao.getAll());
    }

    public WordRepository(Application application) {
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.wordDao();
        wordMapper = new WordDataMapper();
        readAllWords = wordMapper.fromLiveEntityList(localDao.getAll());
    }

    // Get all words
    public LiveData<List<Word>> getAllWords() {
        return readAllWords;
    }

    // Get all words in folder
    public LiveData<List<Word>> getWords(String folderId) {
            return readWords;
    }

    // Get word by id
    public void getWordById(String id) {
        String wordId = id.toString();
        QueryAsyncTask task = new QueryAsyncTask(localDao);
        task.delegate = this;
        task.execute(wordId);
    }

    // Return word
    public MutableLiveData<List<Word>> returnWord() {
        return word;
    }

    // Add word
    public void add(Word word) {
        // -> local db
        WordRepository.InsertAsyncTask task = new WordRepository.InsertAsyncTask(localDao);
        task.delegate = this;
        task.execute(word);

        // -> remote db
        remoteDao = new WordDAO();
        remoteDao.add(word);
    }

    // Update word
    public void update(Word word) {
        // -> local db
        WordRepository.UpdateAsyncTask task = new WordRepository.UpdateAsyncTask(localDao);
        task.execute(word);

        // -> remote db
        remoteDao = new WordDAO();
        remoteDao.update(word.getId(), word);
    }

    // Delete word
    public void delete(Word word) {
        // -> local db
        DeleteAsyncTask task = new DeleteAsyncTask(localDao);
        task.execute(word);

        // -> remote db
        remoteDao = new WordDAO();
        remoteDao.delete(word.getId());
    }

    // Add word locally on login
    public void addOnLogin(Word word) {
        WordRepository.InsertAsyncTask task = new WordRepository.InsertAsyncTask(localDao);
        task.delegate = this;
        task.execute(word);
    }

    // Delete all data
    public void nukeTable() {
        localDao.nukeTable();
    }

    private void asyncQueryFinished(List<Word> results) {
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
            delegate.asyncQueryFinished(result);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordLocalDAO asyncTaskDao;
        private WordRepository delegate = null;
        private final WordDataMapper wordMapper = new WordDataMapper();

        InsertAsyncTask(WordLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
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
            WordEntity wordEntity = wordMapper.mapToEntity(words[0]);
            asyncTaskDao.update(wordEntity);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordLocalDAO asyncTaskDao;
        private final WordDataMapper wordMapper = new WordDataMapper();

        DeleteAsyncTask(WordLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... params) {
            WordEntity wordEntity = wordMapper.mapToEntity(params[0]);
            asyncTaskDao.delete(wordEntity);
            return null;
        }
    }

}
