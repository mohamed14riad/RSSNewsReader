package com.mohamed14riad.rssnewsreader.ui.details;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

import static com.mohamed14riad.rssnewsreader.utils.Constants.ITEM;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    private TextView title, pubDate, link, description;

    private FeedItem item;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(FeedItem item) {
        DetailsFragment detailsFragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ITEM, item);
        detailsFragment.setArguments(args);

        return detailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        title = rootView.findViewById(R.id.title);
        pubDate = rootView.findViewById(R.id.pubDate);
        link = rootView.findViewById(R.id.link);
        description = rootView.findViewById(R.id.description);

        link.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ITEM)) {
            item = getArguments().getParcelable(ITEM);

            if (item != null) {
                title.setText(item.getTitle());
                pubDate.setText(item.getPubDate());
                link.setText(item.getLink());
                description.setText(item.getDescription());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link:
                openCustomTab();
                break;
        }
    }

    private void openCustomTab() {
        String url = link.getText().toString();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }
}
