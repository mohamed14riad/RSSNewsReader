package com.mohamed14riad.rssnewsreader.ui.dialogs.control;

import android.content.Context;

import com.mohamed14riad.rssnewsreader.utils.Store;

import java.util.ArrayList;

public class ControlInteractorImpl implements ControlInteractor {

    private Store store;

    public ControlInteractorImpl(Context context) {
        store = new Store(context);
    }

    @Override
    public void setCheckedProvider(String provider) {
        ArrayList<String> checkedProviders = store.getCheckedProvidersList();
        checkedProviders.add(provider);
        store.setCheckedProvidersList(checkedProviders);
    }

    @Override
    public void setUncheckedProvider(String provider) {
        ArrayList<String> checkedProviders = store.getCheckedProvidersList();
        checkedProviders.remove(provider);
        store.setCheckedProvidersList(checkedProviders);
    }
}
