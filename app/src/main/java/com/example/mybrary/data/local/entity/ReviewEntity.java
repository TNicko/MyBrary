package com.example.mybrary.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "review")
public class ReviewEntity {

    public ReviewEntity(long wordId, long level, Date date_created) {
        this.wordId = wordId;
        this.level = level;
        this.date_created = date_created;
    }

    @PrimaryKey
    @ColumnInfo(name = "word_id")
    public long wordId;

    @ColumnInfo(name = "level")
    public long level;

    @ColumnInfo(name = "date_created")
    public Date date_created;

}
