package com.androidnerdcolony.didyouwork.pages.lists.fragments;

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
import com.androidnerdcolony.didyouwork.pages.create_project.adapter.EntriesRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tiger on 5/2/2017.
 */

public class EntriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    public static final int ENTRIES_LOADER = 21;
    public static final int ALL_ENTRIES_LOADER = 22;


    @BindView(R.id.entries_list)
    RecyclerView entriesListView;


    EntriesRecyclerAdapter mAdapter;
    LoaderManager mLoaderManager;

    Unbinder mUnbinder;
    long projectId;

    public static EntriesFragment newInstance(){
        Bundle bundle = new Bundle();
        EntriesFragment fragment = new EntriesFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entries, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
            projectId = savedInstanceState.getInt(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID);

        }else{
            projectId = -1;
        }

        mAdapter = new EntriesRecyclerAdapter(getContext());

        entriesListView.setAdapter(mAdapter);
        entriesListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLoaderManager = getLoaderManager();
        Loader<Cursor> entriesLoader = mLoaderManager.getLoader(ENTRIES_LOADER);
        if (entriesLoader == null){
            mLoaderManager.initLoader(ENTRIES_LOADER, null, this);
        }else {
            mLoaderManager.restartLoader(ENTRIES_LOADER, savedInstanceState, this);
        }
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri queryUri = DywContract.DywEntries.CONTENT_ENTRIES_URI;
        String selection;
        String [] selectionArgs;
        String sortOrder = "";

        int viewType;
        if (projectId <= 0){
            viewType = 0;
        }else {
            viewType = 1;
        }
        switch (viewType){
            case 0:
                selection = "";
                selectionArgs = new String[]{};
                return new CursorLoader(getContext(), queryUri, DywContract.DywProjection.ENTRIES_PROJECTION, selection, selectionArgs, sortOrder);
            case 1:
                selection = DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID + "=?";
                selectionArgs = new String[]{String.valueOf(projectId)};
                return new CursorLoader(getContext(), queryUri, DywContract.DywProjection.ENTRIES_PROJECTION, selection, selectionArgs, sortOrder);
            default:
                throw new RuntimeException("Loader Not implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.SwapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.SwapCursor(null);

    }
}
