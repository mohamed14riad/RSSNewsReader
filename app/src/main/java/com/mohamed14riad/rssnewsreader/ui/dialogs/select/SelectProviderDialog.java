package com.mohamed14riad.rssnewsreader.ui.dialogs.select;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.ui.listing.ListingPresenter;
import com.mohamed14riad.rssnewsreader.utils.Store;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class SelectProviderDialog extends DialogFragment implements SelectView {

    private SelectPresenter presenter;
    private static ListingPresenter listPresenter;
    private ListView providersList;
    private Store store;

    public static SelectProviderDialog newInstance(ListingPresenter listPresenter) {
        SelectProviderDialog.listPresenter = listPresenter;
        return new SelectProviderDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        presenter = new SelectPresenterImpl(getContext());
        presenter.setView(this);

        store = new Store(getContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_select_provider, null);

        providersList = dialogView.findViewById(R.id.providers_list);

        initViews();

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.select_provider);
        dialog.show();

        return dialog;
    }

    private void initViews() {
        ArrayList<String> providers = store.getCheckedProvidersList();
        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0; i < providers.size(); i++) {
            int index = providers.get(i).indexOf(".com");
            String title;
            if (index > 0) {
                title = providers.get(i).substring(0, index + 4);
            } else {
                title = providers.get(i);
            }
            titles.add(title);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, titles);
        providersList.setAdapter(adapter);
        providersList.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onProviderSelected(providers.get(position));
            listPresenter.displayFeeds();
        });
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.destroy();
    }
}
