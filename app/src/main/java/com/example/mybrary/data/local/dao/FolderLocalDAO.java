package com.example.mybrary.data.local.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mybrary.data.local.entity.FolderEntity;

import java.util.List;

@Dao
public interface FolderLocalDAO{


    @Query("SELECT * FROM folders")
    public LiveData<List<FolderEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(FolderEntity... folder);

    @Delete
    void delete(FolderEntity folder);

    @Update
    void update(FolderEntity... folder);

    @Query("SELECT * FROM folders")
    public Cursor getAllCursor();

    // Clear all data
    @Query("DELETE FROM folders")
    public void nukeTable();

}
