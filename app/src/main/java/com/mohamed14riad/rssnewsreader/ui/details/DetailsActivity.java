package com.mohamed14riad.rssnewsreader.ui.details;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

import static com.mohamed14riad.rssnewsreader.utils.Constants.SELECTED_ITEM;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setToolbar();

        if (getIntent() != null && getIntent().hasExtra(SELECTED_ITEM)) {
            FeedItem item = getIntent().getParcelableExtra(SELECTED_ITEM);

            DetailsFragment detailsFragment = DetailsFragment.newInstance(item);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_container, detailsFragment, DetailsFragment.class.getSimpleName())
                    .commit();
        } else {
            Toast.makeText(this, "Error While Loading Item Details.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(R.string.details);
                actionBar.setDisplayShowTitleEnabled(true);
            }
        }
    }
}
