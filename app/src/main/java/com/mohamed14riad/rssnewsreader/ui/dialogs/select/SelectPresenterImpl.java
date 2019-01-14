package com.mohamed14riad.rssnewsreader.ui.dialogs.select;

import android.content.Context;

public class SelectPresenterImpl implements SelectPresenter {

    private SelectView view;
    private SelectInteractor interactor;

    public SelectPresenterImpl(Context context) {
        interactor = new SelectInteractorImpl(context);
    }

    @Override
    public void setView(SelectView view) {
        this.view = view;
    }

    @Override
    public void onProviderSelected(String provider) {
        if (isViewAttached()) {
            interactor.setSelectedProvider(provider);
            view.dismissDialog();
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
