package com.mohamed14riad.rssnewsreader.data.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class FeedSyncUtils {
    private static final String TAG = FeedSyncUtils.class.getSimpleName() + " : ";

    private static final int SYNC_INTERVAL_MINUTES = 10;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.MINUTES.toSeconds(SYNC_INTERVAL_MINUTES);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 10;

    private static boolean sInitialized;

    private static final String FEED_SYNC_TAG = "feed-sync";

    private static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncSunshineJob = dispatcher.newJobBuilder()
                .setService(FeedFirebaseJobService.class)
                .setTag(FEED_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncSunshineJob);
    }

    synchronized public static void initialize(@NonNull final Context context) {

        if (sInitialized) {
            return;
        }

        sInitialized = true;

        scheduleFirebaseJobDispatcherSync(context);
    }
}
