package com.androidnerdcolony.didyouwork.fragments;

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
import com.androidnerdcolony.didyouwork.data.DywContract;
import com.androidnerdcolony.didyouwork.fragments.adapter.EntriesRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by tiger on 5/2/2017.
 */

public class EntriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String[] ENTRIES_PROJECTION = {
            DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID,
            DywContract.DywEntries.COLUMN_ENTRIES_START_DATE,
            DywContract.DywEntries.COLUMN_ENTRIES_END_DATE,
            DywContract.DywEntries.COLUMN_ENTRIES_TAGS,
            DywContract.DywEntries.COLUMN_ENTRIES_BONUS,
            DywContract.DywEntries.COLUMN_ENTRIES_DESCRIPTION
    };

    public static final int INDEX_ENTRIES_PROJECT_ID = 0;
    public static final int INDEX_ENTIRRES_START_DATE = 1;
    public static final int INDEX_ENTRIES_END_DATE = 2;
    public static final int INDEX_ENTRIES_TAGS = 3;
    public static final int INDEX_ENTRIES_BOUNUS = 4;
    public static final int INDEX_ENDTRIES_DESCRIPTION = 5;


    public static final int ENTRIES_LOADER = 21;
    public static final int ALL_ENTRIES_LOADER = 22;


    @BindView(R.id.entries_list)
    RecyclerView entriesListView;


    EntriesRecyclerAdapter mAdapter;
    LoaderManager mLoaderManager;

    long projectId;

    public static EntriesFragment newInstance(){
        Bundle bundle = new Bundle();
        EntriesFragment fragment = new EntriesFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entries, container, false);
        if (savedInstanceState != null) {
            projectId = savedInstanceState.getInt(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID);

        }else{
            projectId = -1;
        }

        mAdapter = new EntriesRecyclerAdapter(getContext());

        entriesListView.setAdapter(mAdapter);
        entriesListView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                return new CursorLoader(getContext(), queryUri, ENTRIES_PROJECTION, selection, selectionArgs, sortOrder);
            case 1:
                selection = DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID + "=?";
                selectionArgs = new String[]{String.valueOf(projectId)};
                return new CursorLoader(getContext(), queryUri, ENTRIES_PROJECTION, selection, selectionArgs, sortOrder);
            default:
                throw new RuntimeException("Loader Not implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
