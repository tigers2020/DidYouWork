package com.androidnerdcolony.didyouwork.pages.project.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.data.DywContract;

import java.text.NumberFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by tiger on 6/1/2017.
 */

public class ProjectStatsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    final int EntriesLoader = 21;
    @BindView(R.id.project_name)
    TextView projectNameView;
    @BindView(R.id.time_count)
    TextView timeCountView;
    @BindView(R.id.entry_start)
    ImageButton entryStartButton;
    @BindView(R.id.entry_stop)
    ImageButton entryStopButton;
    @BindView(R.id.project_wage)
    TextView projectWageView;
    @BindView(R.id.start_time)
    TextView startTimeView;
    @BindView(R.id.end_time)
    TextView endTimeView;
    @BindView(R.id.work_time_progress)
    ProgressBar workTimeProgress;

    ContentValues entriesValues;
    long projectId;
    Unbinder unBinder;

    long startWorkTime;


    Handler workTimeHandler;

    int proLength;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            proLength = workTimeProgress.getProgress() + 1;
            workTimeProgress.setProgress(proLength);
            timeCountView.setText(String.valueOf(proLength));

            if (proLength < workTimeProgress.getMax()) {
                workTimeHandler.postDelayed(mRunnable, 1);
            } else {
                workTimeProgress.setProgress(0);
                timeCountView.setText("0:00");
                workTimeHandler.post(mRunnable);
            }
        }
    };
    Uri entryId;

    public static ProjectStatsFragment newInstance(long projectId) {

        Bundle args = new Bundle();

        args.putLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, projectId);
        ProjectStatsFragment fragment = new ProjectStatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        projectId = getArguments().getLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID);
        Timber.d("Project ID = " + projectId);
        unBinder = ButterKnife.bind(this, view);

        entriesValues = new ContentValues();

        LoaderManager loaderManager = getLoaderManager();

        Loader<Cursor> entrisLoader = loaderManager.getLoader(EntriesLoader);

        if (entrisLoader == null) {
            loaderManager.initLoader(EntriesLoader, null, this);
        } else {
            loaderManager.restartLoader(EntriesLoader, savedInstanceState, this);
        }

        workTimeHandler = new Handler();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri queryUri = DywContract.DywEntries.CONTENT_PROJECT_URI.buildUpon().appendPath(String.valueOf(projectId)).build();
        String selection = "";
        String[] selectionArg = new String[]{};
        String sortOrder = "";


        return new CursorLoader(getContext(), queryUri, DywContract.DywProjection.PROJECT_PROJECTION, selection, selectionArg, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            String projectName = data.getString(DywContract.DywProjection.INDEX_PROJECT_NAME);
            double wage = data.getDouble(DywContract.DywProjection.INDEX_PROJECT_WAGE);
            final long workTime = data.getLong(DywContract.DywProjection.INDEX_PROJECT_WORK_TIME);

            workTimeProgress.setMax((int) workTime);
            projectNameView.setText(projectName);
            String wageString = NumberFormat.getCurrencyInstance().format(wage);
            projectWageView.setText(wageString);

            entryStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar c = Calendar.getInstance();
                    long startTimeLong = c.getTimeInMillis();
                    long endTimeLong = startTimeLong + workTime;

                    String startTimeString = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " ";
                    c.setTimeInMillis(endTimeLong);
                    String endTimeString = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM);

                    startTimeView.setText(startTimeString);
                    endTimeView.setText(endTimeString);

                    startProgressBar();


                    entriesValues.put(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, projectId);
                    entriesValues.put(DywContract.DywEntries.COLUMN_ENTRIES_START_DATE, startTimeLong);
                    entriesValues.put(DywContract.DywEntries.COLUMN_ENTRIES_END_DATE, endTimeLong);
                    entriesValues.put(DywContract.DywEntries.COLUMN_ENTRIES_ACTIVE, true);

                    entryStartButton.setVisibility(View.GONE);
                    entryStopButton.setVisibility(View.VISIBLE);
                }
            });
            entryStopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    entriesValues.put(DywContract.DywEntries.COLUMN_ENTRIES_ACTIVE, false);
                    String[] selectionArgs = new String[]{};
                    stopProgressBar();
                    entryId = getContext().getContentResolver().insert(DywContract.DywEntries.CONTENT_ENTRIES_URI, entriesValues);

                    if (entryId != null) {
                        Toast.makeText(getContext(), "Work Done, inster data complate", Toast.LENGTH_SHORT).show();
                    }
                    entryStopButton.setVisibility(View.GONE);
                    entryStartButton.setVisibility(View.VISIBLE);
                }
            });
        }

    }


    private void stopProgressBar() {
        entriesValues.put(DywContract.DywEntries.COLUMN_ENTRIES_END_DATE, System.currentTimeMillis());
        workTimeHandler.removeCallbacks(mRunnable);
        workTimeProgress.setProgress(0);
        timeCountView.setText("0:00");
    }

    private void startProgressBar() {
        startWorkTime = System.currentTimeMillis();
        entriesValues.put(DywContract.DywEntries.COLUMN_ENTRIES_START_DATE, startWorkTime);
        workTimeHandler.post(mRunnable);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
