package com.androidnerdcolony.didyouwork.pages.project.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.androidnerdcolony.didyouwork.database.DywContract;
import com.androidnerdcolony.didyouwork.pages.project.adapter.EntriesRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tiger on 6/1/2017.
 */

public class ProjectEntriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    final int ENTRIES_LOADER = 31;
    long projectId;
    Unbinder unBinder;
    EntriesRecyclerAdapter adapter;
    @BindView(R.id.entries_list)
    RecyclerView entriesListView;
    LoaderManager mLoaderManager;

    public static ProjectEntriesFragment newInstance(long projectId) {

        Bundle args = new Bundle();

        args.putLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, projectId);

        ProjectEntriesFragment fragment = new ProjectEntriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entries, container, false);
        unBinder = ButterKnife.bind(this, view);
        projectId = getArguments().getLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID);
        adapter = new EntriesRecyclerAdapter(getContext());
        entriesListView.setAdapter(adapter);
        entriesListView.setLayoutManager(new LinearLayoutManager(getContext()));

        mLoaderManager = getLoaderManager();

        Loader<Cursor> entriesLoader = mLoaderManager.getLoader(ENTRIES_LOADER);

        if (entriesLoader == null) {
            mLoaderManager.initLoader(ENTRIES_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(ENTRIES_LOADER, savedInstanceState, this);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri queryUri = DywContract.DywEntries.CONTENT_ENTRIES_URI;
        String selection = DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID + "=?";
        String[] selectionArg = new String[]{String.valueOf(projectId)};
        String sortOrder = "";


        return new CursorLoader(getContext(), queryUri, DywContract.DywProjection.ENTRIES_PROJECTION, selection, selectionArg, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        entriesListView.setAdapter(adapter);

        adapter.SwapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.SwapCursor(null);
    }
}
