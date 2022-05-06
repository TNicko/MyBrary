package com.example.mybrary.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.mybrary.data.local.DateTypeConverter;

import java.util.Date;


@Entity(tableName = "review")
public class ReviewEntity {

    public ReviewEntity(String wordId, long level, Date date_created, Boolean timer) {
        this.wordId = wordId;
        this.level = level;
        this.date_created = date_created;
        this.timer = timer;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word_id")
    public String wordId;

    @ColumnInfo(name = "level")
    public long level;

    @TypeConverters(DateTypeConverter.class)
    @ColumnInfo(name = "date_created")
    public Date date_created;

    @ColumnInfo(name = "timer")
    public Boolean timer;

}
