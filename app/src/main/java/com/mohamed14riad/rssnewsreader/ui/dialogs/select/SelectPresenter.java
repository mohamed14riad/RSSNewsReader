package com.mohamed14riad.rssnewsreader.ui.dialogs.select;

public interface SelectPresenter {
    void setView(SelectView view);

    void onProviderSelected(String provider);

    void destroy();
}
