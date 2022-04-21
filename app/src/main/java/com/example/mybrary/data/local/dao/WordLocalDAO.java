package com.example.mybrary.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.entity.WordEntity;

import java.util.List;

@Dao
public interface WordLocalDAO {

    @Query("SELECT * FROM words")
    public LiveData<List<WordEntity>> getAll();

    @Insert()
    void add(WordEntity... word);

    @Delete
    void delete(WordEntity word);

    @Update
    void update(WordEntity... word);
}
