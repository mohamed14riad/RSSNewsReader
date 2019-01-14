package com.mohamed14riad.rssnewsreader.ui.dialogs.add;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.utils.Store;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AddProviderDialog extends DialogFragment implements View.OnClickListener {

    private EditText linkET;
    private Button done;
    private Store store;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        store = new Store(getContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_add_provider, null);

        linkET = dialogView.findViewById(R.id.provider_link);
        done = dialogView.findViewById(R.id.done_button);
        done.setOnClickListener(this);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.add_provider);
        dialog.show();

        return dialog;
    }

    @Override
    public void onClick(View view) {
        String link = linkET.getText().toString();

        if (!link.isEmpty()) {
            if (link.startsWith("https://") || link.startsWith("http://")) {
                if (Patterns.WEB_URL.matcher(link).matches()) {
                    if (!link.endsWith("/")) {
                        link = link.concat("/");
                    }

                    int index1 = link.indexOf("://");
                    String subLink1 = link.substring(index1 + 3);

                    ArrayList<String> providers = store.getProvidersList();
                    boolean isFound = false;

                    for (int i = 0; i < providers.size(); i++) {
                        String provider = providers.get(i);

                        int index2 = provider.indexOf("://");
                        String subLink2 = provider.substring(index2 + 3);

                        if (subLink1.equals(subLink2)) {
                            isFound = true;
                        }
                    }

                    if (!isFound) {
                        providers.add(link);
                        store.setProvidersList(providers);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.provider_exist), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.invalid_provider_error), Toast.LENGTH_SHORT).show();
                }
            } else {
                link = "https://".concat(link);
                if (Patterns.WEB_URL.matcher(link).matches()) {
                    if (!link.endsWith("/")) {
                        link = link.concat("/");
                    }

                    int index1 = link.indexOf("://");
                    String subLink1 = link.substring(index1 + 3);

                    ArrayList<String> providers = store.getProvidersList();
                    boolean isFound = false;

                    for (int i = 0; i < providers.size(); i++) {
                        String provider = providers.get(i);

                        int index2 = provider.indexOf("://");
                        String subLink2 = provider.substring(index2 + 3);

                        if (subLink1.equals(subLink2)) {
                            isFound = true;
                        }
                    }

                    if (!isFound) {
                        providers.add(link);
                        store.setProvidersList(providers);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.provider_exist), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.invalid_provider_error), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.enter_provider), Toast.LENGTH_SHORT).show();
        }
    }
}
