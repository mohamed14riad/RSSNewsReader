package com.mohamed14riad.rssnewsreader.ui.dialogs.select;

import android.content.Context;

import com.mohamed14riad.rssnewsreader.utils.Store;

public class SelectInteractorImpl implements SelectInteractor {

    private Store store;

    public SelectInteractorImpl(Context context) {
        store = new Store(context);
    }

    @Override
    public void setSelectedProvider(String provider) {
        store.setSelectedProvider(provider);
    }

    @Override
    public String getSelectedProvider() {
        return store.getSelectedProvider();
    }
}
