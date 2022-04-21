package com.example.mybrary.data.local;

import android.content.Context;
import android.os.strictmode.InstanceCountViolation;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mybrary.data.local.dao.FolderLocalDAO;
import com.example.mybrary.data.local.dao.WordLocalDAO;
import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.entity.WordEntity;

@Database(entities = {FolderEntity.class, WordEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FolderLocalDAO folderDao();
    public abstract WordLocalDAO wordDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "MyBrary")
                    .allowMainThreadQueries()
//                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }

//    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `words` (`id` LONG, " +
//                    "`folder_id` LONG, " +
//                    "`word` TEXT, " +
//                    "`translation` TEXT, " +
//                    "`notes` TEXT, " +
//                    "`review` BOOLEAN, PRIMARY KEY(`id`))");
//        }
//    };
}
