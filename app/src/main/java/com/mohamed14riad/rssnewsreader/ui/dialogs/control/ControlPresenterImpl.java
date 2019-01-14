package com.mohamed14riad.rssnewsreader.ui.dialogs.control;

import android.content.Context;

public class ControlPresenterImpl implements ControlPresenter {

    private ControlView view;
    private ControlInteractor interactor;

    public ControlPresenterImpl(Context context) {
        interactor = new ControlInteractorImpl(context);
    }

    @Override
    public void setView(ControlView view) {
        this.view = view;
    }

    @Override
    public void onProviderSelected(String provider, boolean checked) {
        if (isViewAttached()) {
            if (checked) {
                interactor.setCheckedProvider(provider);
            } else {
                interactor.setUncheckedProvider(provider);
            }
        }
    }

    @Override
    public void destroy() {
        view = null;
    }

    private boolean isViewAttached() {
        return view != null;
    }
}
