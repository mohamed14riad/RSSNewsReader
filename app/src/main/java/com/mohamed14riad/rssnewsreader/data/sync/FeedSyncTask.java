package com.mohamed14riad.rssnewsreader.data.sync;

import android.content.Context;

import com.mohamed14riad.rssnewsreader.data.database.AppDatabase;
import com.mohamed14riad.rssnewsreader.data.models.Channel;
import com.mohamed14riad.rssnewsreader.data.models.Feed;
import com.mohamed14riad.rssnewsreader.data.models.FeedItem;
import com.mohamed14riad.rssnewsreader.data.network.ApiClient;
import com.mohamed14riad.rssnewsreader.data.network.ApiService;
import com.mohamed14riad.rssnewsreader.utils.AppExecutors;
import com.mohamed14riad.rssnewsreader.utils.Store;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedSyncTask {
    private static final String TAG = FeedSyncTask.class.getSimpleName() + " : ";

    private Context context;

    synchronized public void syncFeeds(Context context) {
        this.context = context;

        try {
            Store store = new Store(context);
            String selectedProvider = store.getSelectedProvider();

            ApiService apiService = ApiClient.getApiService(selectedProvider);

            Disposable disposable = apiService.getNews(selectedProvider)
                    .map(Feed::getChannel)
                    .map(Channel::getFeedItems)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onFeedFetchSuccess, this::onFeedFetchFailed);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onFeedFetchSuccess(List<FeedItem> feeds) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.feedsDao().deleteFeeds();
                database.feedsDao().insertFeeds(feeds);
            }
        });
    }

    private void onFeedFetchFailed(Throwable e) {
        e.printStackTrace();
    }
}
