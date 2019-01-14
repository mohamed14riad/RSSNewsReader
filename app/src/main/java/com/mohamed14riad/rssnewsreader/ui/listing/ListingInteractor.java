package com.mohamed14riad.rssnewsreader.ui.listing;

import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

import java.util.List;

import io.reactivex.Observable;

public interface ListingInteractor {
    Observable<List<FeedItem>> fetchFeeds();
}
