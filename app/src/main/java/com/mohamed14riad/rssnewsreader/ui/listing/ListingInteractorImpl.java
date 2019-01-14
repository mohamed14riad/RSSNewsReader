package com.mohamed14riad.rssnewsreader.ui.listing;

import android.content.Context;

import com.mohamed14riad.rssnewsreader.data.models.Channel;
import com.mohamed14riad.rssnewsreader.data.models.Feed;
import com.mohamed14riad.rssnewsreader.data.models.FeedItem;
import com.mohamed14riad.rssnewsreader.data.network.ApiClient;
import com.mohamed14riad.rssnewsreader.data.network.ApiService;
import com.mohamed14riad.rssnewsreader.utils.Store;

import java.util.List;

import io.reactivex.Observable;

public class ListingInteractorImpl implements ListingInteractor{

    private Store store;

    public ListingInteractorImpl(Context context) {
        store = new Store(context);
    }

    @Override
    public Observable<List<FeedItem>> fetchFeeds() {
        String selectedProvider = store.getSelectedProvider();

        ApiService apiService = ApiClient.getApiService(selectedProvider);
        return apiService.getNews(selectedProvider).map(Feed::getChannel).map(Channel::getFeedItems);
    }
}
