package com.mohamed14riad.rssnewsreader.ui.listing;

import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

import java.util.List;

public interface ListingView {
    void showFeeds(List<FeedItem> feedItems);

    void loadingStarted();

    void loadingFailed(String errorMessage);

    void onItemClicked(FeedItem feedItem);
}
