package com.example.mybrary.data.repository;

import com.example.mybrary.data.firebase.FolderDAO;
import com.example.mybrary.data.firebase.WordDAO;
import com.example.mybrary.data.local.FolderLocalDAO;
import com.example.mybrary.domain.model.Word;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class WordRepository {

    WordDAO remoteDao;

    public WordRepository(String folderName) {
        remoteDao = new WordDAO(folderName);
    }

    // Get all words
    public List<Word> getAllWords() {
        return remoteDao.getAllWords();
    }

    //Get word by id
    public Word getWordById() {
        return remoteDao.getWordById();
    }

    // Add word
    public void add(Word word) {
        remoteDao.add(word);
    }

    // Edit word
    public void update(String key, HashMap<String, Object> hashMap) {
        remoteDao.update(key, hashMap);
    }

    // Remove word
    public void delete(String key) {
        remoteDao.delete(key);
    }

}
