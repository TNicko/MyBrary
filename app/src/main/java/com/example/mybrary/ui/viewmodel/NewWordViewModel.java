package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Word;

public class NewWordViewModel extends AndroidViewModel {

    private WordRepository wordRepo;

    public NewWordViewModel(Application application) {
        super(application);
        wordRepo = new WordRepository(application);
    }

    public void addWord(Word word) {
        wordRepo.add(word);
    }

    public String checkWordInput(String word, String translation, String notes, Boolean review, Long folderId) {

        if (word.equals("")) {
            return "word null";
        } else if (translation.equals("")) {
            return "translation null";
        } else {
            Word newWord = new Word(0, folderId, word, translation, notes, review);
            wordRepo.add(newWord);
            return "saved";
        }

    }
}
