package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FolderViewModelFactory implements ViewModelProvider.Factory{

    private Application mApplication;
    private Long mFolderId;

    public FolderViewModelFactory(Application application, Long folderId) {
        mApplication = application;
        mFolderId = folderId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FolderViewModel(mApplication, mFolderId);
    }
}
