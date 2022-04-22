package com.example.mybrary.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.entity.WordEntity;
import com.example.mybrary.domain.model.Word;

import java.util.List;

@Dao
public interface WordLocalDAO {

    @Query("SELECT * FROM words WHERE folder_id=:folderId")
    public LiveData<List<WordEntity>> getAll(Long folderId);

    @Query("SELECT * FROM words WHERE id=:id")
    public List<WordEntity> getById(String id);

    @Insert()
    void add(WordEntity... word);

    @Delete
    void delete(WordEntity word);

    @Update
    void update(WordEntity... word);
}
