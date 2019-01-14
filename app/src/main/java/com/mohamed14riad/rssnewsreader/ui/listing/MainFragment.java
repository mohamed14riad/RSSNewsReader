package com.mohamed14riad.rssnewsreader.ui.listing;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.data.database.AppDatabase;
import com.mohamed14riad.rssnewsreader.data.models.FeedItem;
import com.mohamed14riad.rssnewsreader.ui.details.DetailsActivity;
import com.mohamed14riad.rssnewsreader.ui.details.DetailsFragment;
import com.mohamed14riad.rssnewsreader.ui.dialogs.add.AddProviderDialog;
import com.mohamed14riad.rssnewsreader.ui.dialogs.control.ControlProvidersDialog;
import com.mohamed14riad.rssnewsreader.ui.dialogs.select.SelectProviderDialog;
import com.mohamed14riad.rssnewsreader.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.mohamed14riad.rssnewsreader.ui.listing.MainActivity.twoPaneMode;
import static com.mohamed14riad.rssnewsreader.utils.Constants.SELECTED_ITEM;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment
        implements ListingView, SwipeRefreshLayout.OnRefreshListener {

    private ListingPresenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private FeedsAdapter adapter;

    private AppDatabase database;

    private List<FeedItem> feedItems;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        presenter = new ListingPresenterImpl(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        refreshLayout = rootView.findViewById(R.id.refresh_Layout);
        recyclerView = rootView.findViewById(R.id.news_recycler_view);

        refreshLayout.setOnRefreshListener(this);

        adapter = new FeedsAdapter(getContext(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        database = AppDatabase.getInstance(getContext().getApplicationContext());

        feedItems = new ArrayList<>();
        feedItems.clear();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_provider:
                displaySelectProviderDialog();
                return true;
            case R.id.action_add_provider:
                displayAddProviderDialog();
                return true;
            case R.id.action_control_providers:
                displayControlProvidersDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectProviderDialog() {
        DialogFragment selectDialogFragment = SelectProviderDialog.newInstance(presenter);
        selectDialogFragment.show(getFragmentManager(), "Select");
    }

    private void displayAddProviderDialog() {
        DialogFragment addDialogFragment = new AddProviderDialog();
        addDialogFragment.show(getFragmentManager(), "Add");
    }

    private void displayControlProvidersDialog() {
        DialogFragment controlDialogFragment = new ControlProvidersDialog();
        controlDialogFragment.show(getFragmentManager(), "Control");
    }

    @Override
    public void onRefresh() {
        presenter.displayFeeds();
    }

    @Override
    public void showFeeds(List<FeedItem> items) {
        feedItems.clear();
        feedItems = items;
        adapter.setItems(feedItems);

        refreshLayout.setRefreshing(false);

        if (!feedItems.isEmpty()) {
            if (twoPaneMode) {
                loadDetailsFragment(feedItems.get(0));
            }
        } else {
            Toast.makeText(getContext(), "Feeds List Is Empty!", Toast.LENGTH_SHORT).show();
        }

        if (isConnected()) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    database.feedsDao().deleteFeeds();
                    database.feedsDao().insertFeeds(items);
                }
            });
        }
    }

    @Override
    public void loadingStarted() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void loadingFailed(String errorMessage) {
        if (isConnected()) {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<FeedItem> feedItems = database.feedsDao().loadFeeds();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showFeeds(feedItems);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onItemClicked(FeedItem feedItem) {
        if (twoPaneMode) {
            loadDetailsFragment(feedItem);
        } else {
            startDetailsActivity(feedItem);
        }
    }

    private void loadDetailsFragment(FeedItem item) {
        if (item != null) {
            DetailsFragment detailsFragment = DetailsFragment.newInstance(item);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_container, detailsFragment, DetailsFragment.class.getSimpleName())
                    .commit();
        }
    }

    private void startDetailsActivity(FeedItem item) {
        if (item != null) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(SELECTED_ITEM, item);
            startActivity(intent);
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}
