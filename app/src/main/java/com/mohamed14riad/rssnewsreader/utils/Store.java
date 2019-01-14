package com.mohamed14riad.rssnewsreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mohamed14riad.rssnewsreader.utils.Constants.FIRST_TIME;
import static com.mohamed14riad.rssnewsreader.utils.Constants.GIZMODO_BASE_URL;
import static com.mohamed14riad.rssnewsreader.utils.Constants.LIST_KEY;
import static com.mohamed14riad.rssnewsreader.utils.Constants.LIST_KEY_CHECKED;
import static com.mohamed14riad.rssnewsreader.utils.Constants.MASHABLE_BASE_URL;
import static com.mohamed14riad.rssnewsreader.utils.Constants.PREF_NAME;
import static com.mohamed14riad.rssnewsreader.utils.Constants.SELECTED_PROVIDER;
import static com.mohamed14riad.rssnewsreader.utils.Constants.TECHCRUNCH_BASE_URL;

public class Store {
    private SharedPreferences pref;

    public Store(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setSelectedProvider(String selectedProvider) {
        pref.edit().putString(SELECTED_PROVIDER, selectedProvider).apply();
    }

    public String getSelectedProvider() {
        return pref.getString(SELECTED_PROVIDER, TECHCRUNCH_BASE_URL);
    }

    public void setProvidersList(ArrayList<String> providers) {
        String[] providersList = providers.toArray(new String[providers.size()]);
        pref.edit().putString(LIST_KEY, TextUtils.join("‚‗‚", providersList)).apply();
    }

    public ArrayList<String> getProvidersList() {
        return new ArrayList<>(Arrays.asList(TextUtils.split(pref.getString(LIST_KEY, null), "‚‗‚")));
    }

    public void setCheckedProvidersList(ArrayList<String> providers) {
        String[] providersList = providers.toArray(new String[providers.size()]);
        pref.edit().putString(LIST_KEY_CHECKED, TextUtils.join("‚‗‚", providersList)).apply();
    }

    public ArrayList<String> getCheckedProvidersList() {
        return new ArrayList<>(Arrays.asList(TextUtils.split(pref.getString(LIST_KEY_CHECKED, null), "‚‗‚")));
    }

    private void setDefaultList() {
        ArrayList<String> defaultList = new ArrayList<>();
        defaultList.add(TECHCRUNCH_BASE_URL);
        defaultList.add(MASHABLE_BASE_URL);
        defaultList.add(GIZMODO_BASE_URL);

        setProvidersList(defaultList);
        setCheckedProvidersList(defaultList);
    }

    public void initialize() {
        if (!pref.getBoolean(FIRST_TIME, false)) {
            // one time code runs here
            setDefaultList();

            // first time has run.
            pref.edit().putBoolean(FIRST_TIME, true).apply();
        }
    }
}
