package com.androidnerdcolony.didyouwork.pages.project.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.database.DywContract;

/**
 * Fragment for Edit ProjectDataStructure details.
 */

public class ProjectSettingsFragment extends Fragment {

    long projectId;

    public static ProjectSettingsFragment newInstance(long projectId) {

        Bundle args = new Bundle();
        args.putLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, projectId);
        ProjectSettingsFragment fragment = new ProjectSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_project, container, false);

        return view;
    }
}
