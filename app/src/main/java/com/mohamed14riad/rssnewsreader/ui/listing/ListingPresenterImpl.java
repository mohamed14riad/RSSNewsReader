package com.mohamed14riad.rssnewsreader.ui.listing;

import android.content.Context;

import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListingPresenterImpl implements ListingPresenter {

    private ListingView view;
    private ListingInteractor interactor;
    private Disposable disposable;

    public ListingPresenterImpl(Context context) {
        interactor = new ListingInteractorImpl(context);
    }

    @Override
    public void setView(ListingView view) {
        this.view = view;
        displayFeeds();
    }

    @Override
    public void displayFeeds() {
        showLoading();

        disposable = interactor.fetchFeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFeedFetchSuccess, this::onFeedFetchFailed);
    }

    @Override
    public void destroy() {
        view = null;

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    private void showLoading() {
        if (isViewAttached()) {
            view.loadingStarted();
        }
    }

    private void onFeedFetchSuccess(List<FeedItem> feeds) {
        if (isViewAttached()) {
            view.showFeeds(feeds);
        }
    }

    private void onFeedFetchFailed(Throwable e) {
        view.loadingFailed(e.getMessage());
    }
}
