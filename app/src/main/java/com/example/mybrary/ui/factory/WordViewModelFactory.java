package com.example.mybrary.ui.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybrary.domain.model.Word;
import com.example.mybrary.ui.viewmodel.UpdateWordViewModel;

public class WordViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private Long mWordId;

    public WordViewModelFactory(Application application, Long wordId) {
        mApplication = application;
        mWordId = wordId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UpdateWordViewModel(mApplication, mWordId);
    }
}
