package com.mohamed14riad.rssnewsreader.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

import java.util.List;

@Dao
public interface FeedsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFeeds(List<FeedItem> feedItems);

    @Query("DELETE FROM FeedItem")
    void deleteFeeds();

    @Query("SELECT * FROM FeedItem")
    List<FeedItem> loadFeeds();
}
