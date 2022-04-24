package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Delete;

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
    private LiveData<List<Word>> readAllWords;
    private MutableLiveData<List<Word>> word = new MutableLiveData<>();
    private MutableLiveData<Long> wordId = new MutableLiveData<>();

    public WordRepository(Application application, Long folderId) {
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

    private final boolean isLocal = true;

    // Get all words
    public LiveData<List<Word>> getAllWords() {
        return readAllWords;
    }

    // Get all words in folder
    public LiveData<List<Word>> getWords(Long folderId) {
        if (isLocal) {
            return readWords;
        } else {
            return null;
        }
    }

    // Get word by id
    public void getWordById(Long id) {
        String wordId = id.toString();
        QueryAsyncTask task = new QueryAsyncTask(localDao);
        task.delegate = this;
        task.execute(wordId);
    }

    // Return word
    public MutableLiveData<List<Word>> returnWord() {
        return word;
    }

    // Return generated word ID
    public MutableLiveData<Long> returnWordId() {
        System.out.println("word id returning... "+wordId);
        return wordId;
    }

    // Add word
    public void add(Word word) {
        WordRepository.InsertAsyncTask task = new WordRepository.InsertAsyncTask(localDao);
        task.delegate = this;
        task.execute(word);
        System.out.println("repo word id: "+ wordId);
    }

    // Update word
    public void update(Word word) {
        WordRepository.UpdateAsyncTask task = new WordRepository.UpdateAsyncTask(localDao);
        task.execute(word);
    }

    // Delete word
    public void delete(Word word) {
        DeleteAsyncTask task = new DeleteAsyncTask(localDao);
        task.execute(word);
    }

    private void asyncQueryFinished(List<Word> results) {
        word.setValue(results);
    }

    // Set generated word ID
    private void asyncAddFinished(List<Long> id) {
        System.out.println("setValue id = "+id);
        wordId.setValue(id.get(0));
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

    private static class InsertAsyncTask extends AsyncTask<Word, Void, List<Long>> {

        private WordLocalDAO asyncTaskDao;
        private WordRepository delegate = null;
        private final WordDataMapper wordMapper = new WordDataMapper();

        InsertAsyncTask(WordLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Long> doInBackground(Word... words) {
            System.out.println(words[0]);
            WordEntity wordEntity = wordMapper.mapToEntity(words[0]);
            return asyncTaskDao.add(wordEntity);
        }

        @Override
        protected void onPostExecute(List<Long> aLong) {
            System.out.println("long value on Post = "+aLong);
            delegate.asyncAddFinished(aLong);
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
