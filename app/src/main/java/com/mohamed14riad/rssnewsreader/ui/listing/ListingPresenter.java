package com.mohamed14riad.rssnewsreader.ui.listing;

public interface ListingPresenter {
    void setView(ListingView view);

    void displayFeeds();

    void destroy();
}
