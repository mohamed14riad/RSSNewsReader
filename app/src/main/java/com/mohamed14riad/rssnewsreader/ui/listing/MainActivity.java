package com.mohamed14riad.rssnewsreader.ui.listing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.data.sync.FeedSyncUtils;
import com.mohamed14riad.rssnewsreader.utils.Store;

public class MainActivity extends AppCompatActivity {

    public static boolean twoPaneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        if (findViewById(R.id.details_container) != null) {
            twoPaneMode = true;
        } else {
            twoPaneMode = false;
        }

        MainFragment mainFragment = MainFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_container, mainFragment, MainFragment.class.getSimpleName())
                .commit();

        Store store = new Store(this);
        store.initialize();

        FeedSyncUtils.initialize(this);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
