package com.mohamed14riad.rssnewsreader.ui.dialogs.control;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.utils.Store;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ControlProvidersDialog extends DialogFragment implements ControlView {

    private ControlPresenter presenter;
    private LinearLayout controlLayout;
    private Store store;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        presenter = new ControlPresenterImpl(getContext());
        presenter.setView(this);
        store = new Store(getContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_control_providers, null);

        controlLayout = dialogView.findViewById(R.id.control_layout);
        controlLayout.removeAllViews();

        initViews();

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.control_providers);
        dialog.show();

        return dialog;
    }

    private void initViews() {
        ArrayList<String> providers = store.getProvidersList();
        ArrayList<String> checkedProviders = store.getCheckedProvidersList();

        for (int i = 0; i < providers.size(); i++) {
            String provider = providers.get(i);

            boolean isChecked = false;
            for (int j = 0; j < checkedProviders.size(); j++) {
                if (provider.equals(checkedProviders.get(j))) {
                    isChecked = true;
                }
            }

            int index = providers.get(i).indexOf(".com");
            String title;
            if (index > 0) {
                title = providers.get(i).substring(0, index + 4);
            } else {
                title = providers.get(i);
            }

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(title);

            // The first 3 items are default providers
            if (i <= 2) {
                checkBox.setChecked(true);
                checkBox.setEnabled(false);
            } else {
                if (isChecked) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }

            int position = i;
            checkBox.setOnClickListener(view -> {
                // Is the view now checked?
                boolean checked = ((CheckBox) view).isChecked();
                presenter.onProviderSelected(providers.get(position), checked);
            });

            controlLayout.addView(checkBox);
        }
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
