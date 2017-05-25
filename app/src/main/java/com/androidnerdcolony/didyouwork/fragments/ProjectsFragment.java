package com.androidnerdcolony.didyouwork.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.data.DywContract;
import com.androidnerdcolony.didyouwork.fragments.adapter.ProjectRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tiger on 5/2/2017.
 */

public class ProjectsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String[] PROJECT_PROJECTION = {
            DywContract.DywEntries._ID,
            DywContract.DywEntries.COLUMN_PROJECT_NAME,
            DywContract.DywEntries.COLUMN_PROJECT_CREATED_DATE,
            DywContract.DywEntries.COLUMN_PROJECT_WAGE,
            DywContract.DywEntries.COLUMN_PROJECT_LOCATION,
            DywContract.DywEntries.COLUMN_PROJECT_TAGS,
            DywContract.DywEntries.COLUMN_PROJECT_DEAD_LINE,
            DywContract.DywEntries.COLUMN_PROJECT_WORK_TIME,
            DywContract.DywEntries.COLUMN_PROJECT_TIME_ROUNDING,
            DywContract.DywEntries.COLUMN_PROJECT_TYPE,
            DywContract.DywEntries.COLUMN_PROJECT_LAST_ACTIVITY,
            DywContract.DywEntries.COLUMN_PROJECT_DESCRIPTION
    };

    public static final int INDEX_PROJECT_ID = 0;
    public static final int INDEX_PROJECT_NAME = 1;
    public static final int INDEX_PROJECT_CREATED_DATE = 2;
    public static final int INDEX_PROJECT_WAGE = 3;
    public static final int INDEX_PROJECT_LOCATION = 4;
    public static final int INDEX_PROJECT_TAGS = 5;
    public static final int INDEX_PROJECT_DEAD_LINE = 6;
    public static final int INDEX_PROJECT_WORK_TIME = 7;
    public static final int INDEX_PROJECT_TIME_ROUNDING = 8;
    public static final int INDEX_PROJECT_TYPE = 9;
    public static final int INDEX_PROJECT_LAST_ACTIVITY = 10;
    public static final int INDEX_PROJECT_DESCRIPTION = 11;

    public static final int PROJECT_LOADER = 11;

    @BindView(R.id.fab_create_new_project)
    FloatingActionButton createNewProjectButton;
    @BindView(R.id.project_list)
    RecyclerView projectListView;
    Unbinder mUnbinder;

    LoaderManager mLoaderManager;
    ProjectRecyclerAdapter mAdapter;
    public static ProjectsFragment newInstance(){
        ProjectsFragment fragment = new ProjectsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mAdapter = new ProjectRecyclerAdapter(getContext());


        projectListView.setAdapter(mAdapter);
        projectListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLoaderManager = getLoaderManager();
        Loader<Cursor> projectLoader = mLoaderManager.getLoader(PROJECT_LOADER);
        if (projectLoader == null){
            mLoaderManager.initLoader(PROJECT_LOADER, null, this);
        }else {
            mLoaderManager.restartLoader(PROJECT_LOADER, savedInstanceState, this);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri queryUri = DywContract.DywEntries.CONTENT_PROJECT_URI;
        String selection = "";
        String[] selectArgs = new String[]{};
        String sortOrder = "";

        return new CursorLoader(getContext(), queryUri, PROJECT_PROJECTION, selection, selectArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
}
