package com.mohamed14riad.rssnewsreader.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

@Database(entities = {FeedItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();

    private static AppDatabase instance;
    private static final String DATABASE_NAME = "RSS";
    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return instance;
    }

    public abstract FeedsDao feedsDao();
}
