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

    @Insert()
    void add(ReviewEntity... review);

    @Query("SELECT * FROM review")
    void delete(ReviewEntity review);

    @Update
    void update(ReviewEntity... review);
}
