package com.example.mybrary.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.local.dao.FolderLocalDAO;
import com.example.mybrary.data.local.dao.ReviewLocalDAO;
import com.example.mybrary.data.local.dao.WordLocalDAO;

import org.checkerframework.checker.units.qual.A;

public class MyBraryContentProvider extends ContentProvider {

    private SQLiteDatabase database;

    // FOR DATA
    private static final String AUTHORITY = "com.example.mybrary.data.provider";
    private static final String TABLE_FOLDERS = "folders";
    private static final String TABLE_WORDS = "words";
    private static final String TABLE_REVIEWS = "reviews";
//    public static final Uri URI_ITEM = Uri.parse("content://"+AUTHORITY+"/"+TABLE_FOLDERS);

    private static final int FOLDERS = 1;
    private static final int FOLDERS_ID = 2;
    private static final int WORDS = 3;
    private static final int WORDS_ID = 4;
    private static final int FOLDER_WORDS = 5;
    private static final int REVIEWS = 6;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, TABLE_FOLDERS, FOLDERS);
        uriMatcher.addURI(AUTHORITY, TABLE_FOLDERS+"/#", FOLDERS_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_WORDS, WORDS);
        uriMatcher.addURI(AUTHORITY, TABLE_WORDS+"/#", WORDS_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_FOLDERS+"/#/"+TABLE_WORDS, FOLDER_WORDS);
        uriMatcher.addURI(AUTHORITY, TABLE_REVIEWS, REVIEWS);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        int res = uriMatcher.match(uri);
        final Context context = getContext();
        if (context == null) {
            return null;
        }
        AppDatabase appDb = AppDatabase.getDbInstance(context);
        final Cursor cursor;
        switch(res) {
            case FOLDERS:
                // create a results cursor with all FOLDERS result
                cursor = appDb.folderDao().getAllCursor();
                break;
            case WORDS:
                cursor = appDb.wordDao().getAllCursor();
                break;
            case FOLDER_WORDS:
                cursor = appDb.wordDao().getAllByFolderCursor(String.valueOf(uri));
                break;
            default:
                throw new IllegalArgumentException("Unkown URI: " + uri);

        }
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case FOLDERS:
                return "vnd.android.cursor.dir/folders";
            case WORDS:
                return "vnd.android.cursor.dir/words";
            case FOLDER_WORDS:
                return "vnd.android.cursor.dir/folders/words";
            case WORDS_ID:
                return "vnd.android.cursor.item/words";
            default:
                throw new IllegalArgumentException("This is an unkown URI: "+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
