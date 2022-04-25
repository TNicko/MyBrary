package com.example.mybrary.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.entity.ReviewEntity;
import com.example.mybrary.domain.model.Review;

import java.util.List;

@Dao
public interface ReviewLocalDAO {

    @Query("SELECT * FROM review")
    public LiveData<List<ReviewEntity>> getAll();

    @Query("SELECT * FROM review WHERE word_id=:id")
    public List<ReviewEntity> getById(String id);

    @Query("UPDATE review SET timer=:timer WHERE word_id=:id")
    void updateTimer(Boolean timer, long id);

    @Insert()
    void add(ReviewEntity... review);

    @Delete
    void delete(ReviewEntity review);

    @Update
    void update(ReviewEntity... review);
}
