package com.example.mybrary.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "folders")
public class FolderEntity {

    public FolderEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "name")
    public String name;

}
