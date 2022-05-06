package com.example.mybrary.data.local.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.entity.WordEntity;
import com.example.mybrary.domain.model.Word;

import java.util.List;

@Dao
public interface WordLocalDAO {

    @Query("SELECT * FROM words")
    public LiveData<List<WordEntity>> getAll();

    @Query("SELECT * FROM words WHERE folder_id=:folderId")
    public LiveData<List<WordEntity>> getAllByFolder(String folderId);

    @Query("SELECT * FROM words WHERE id=:id")
    public List<WordEntity> getById(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(WordEntity... word);

    @Delete
    void delete(WordEntity word);

    @Update
    void update(WordEntity... word);

    @Query("SELECT * FROM words")
    public Cursor getAllCursor();

    @Query("SELECT * FROM words WHERE folder_id=:folderId")
    public Cursor getAllByFolderCursor(String folderId);

    @Query("SELECT * FROM words WHERE id=:id")
    public Cursor getByIdCursor(String id);

    // Clear all data
    @Query("DELETE FROM words")
    public void nukeTable();
}
