package com.example.mybrary.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;

@Entity(tableName = "words")
public class WordEntity {

    public WordEntity(String id, String folder_id, String word, String translation, String notes, boolean review) {
        this.id = id;
        this.folder_id = folder_id;
        this.word = word;
        this.translation = translation;
        this.notes = notes;
        this.review = review;
    }

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "folder_id")
    public String folder_id;

    @ColumnInfo(name = "word")
    public String word;

    @ColumnInfo(name = "translation")
    public String translation;

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "review")
    public boolean review;
}
