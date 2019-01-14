package com.mohamed14riad.rssnewsreader.ui.dialogs.control;

public interface ControlPresenter {
    void setView(ControlView view);

    void onProviderSelected(String provider, boolean checked);

    void destroy();
}
