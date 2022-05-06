package com.example.mybrary.ui.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybrary.ui.viewmodel.FolderViewModel;

public class FolderViewModelFactory implements ViewModelProvider.Factory{

    private Application mApplication;
    private String mFolderId;

    public FolderViewModelFactory(Application application, String folderId) {
        mApplication = application;
        mFolderId = folderId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FolderViewModel(mApplication, mFolderId);
    }
}
