package com.mohamed14riad.rssnewsreader.data.sync;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class FeedFirebaseJobService extends JobService {
    private final String TAG = FeedFirebaseJobService.class.getSimpleName() + " : ";

    private AsyncTask<Void, Void, Void> mFetchFeedTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchFeedTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                Context context = getApplicationContext();
                new FeedSyncTask().syncFeeds(context);
                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }
        };

        mFetchFeedTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchFeedTask != null) {
            mFetchFeedTask.cancel(true);
        }
        return true;
    }
}
