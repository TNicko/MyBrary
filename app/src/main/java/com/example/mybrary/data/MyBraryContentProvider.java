package com.example.mybrary.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.local.dao.FolderLocalDAO;
import com.example.mybrary.data.local.dao.ReviewLocalDAO;
import com.example.mybrary.data.local.dao.WordLocalDAO;

import org.checkerframework.checker.units.qual.A;

public class MyBraryContentProvider extends ContentProvider {

    private AppDatabase localDB;


    @Override
    public boolean onCreate() {
        localDB = Room.databaseBuilder(getContext(), AppDatabase.class, "mybrary.db").allowMainThreadQueries().build();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
