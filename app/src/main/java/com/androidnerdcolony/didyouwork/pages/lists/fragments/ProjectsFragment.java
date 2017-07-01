package com.androidnerdcolony.didyouwork.pages.lists.fragments;

import android.content.Intent;
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
import com.androidnerdcolony.didyouwork.database.DywContract;
import com.androidnerdcolony.didyouwork.pages.create_project.CreateProjectActivity;
import com.androidnerdcolony.didyouwork.pages.lists.adater.ProjectRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tiger on 5/2/2017.
 */

public class ProjectsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

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

        createNewProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateProjectActivity.class);
                startActivity(intent);

            }
        });
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

        return new CursorLoader(getContext(), queryUri, DywContract.DywProjection.PROJECT_PROJECTION, selection, selectArgs, sortOrder);
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
