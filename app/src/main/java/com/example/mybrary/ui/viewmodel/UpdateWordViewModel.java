package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Word;

import java.util.List;

public class UpdateWordViewModel extends AndroidViewModel {

    private WordRepository wordRepo;
    public MutableLiveData<List<Word>> word;
    private Long wordId;

    public UpdateWordViewModel(Application application, Long id) {
        super(application);
        wordId = id;
        wordRepo = new WordRepository(application);
        getWord(id);
        word = wordRepo.returnWord();
    }

    public MutableLiveData<List<Word>> returnWord() {
        return word;
    }

    public void deleteWord() {
        wordRepo.delete(word.getValue().get(0));
    }

    public void updateWord(Word word) {
        wordRepo.update(word);
    }

    public void getWord(Long wordId){
        wordRepo.getWordById(wordId);
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
