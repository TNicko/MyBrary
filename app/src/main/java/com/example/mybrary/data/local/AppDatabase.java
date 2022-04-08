package com.example.mybrary.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FolderEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FolderLocalDAO folderDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "MyBrary")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
