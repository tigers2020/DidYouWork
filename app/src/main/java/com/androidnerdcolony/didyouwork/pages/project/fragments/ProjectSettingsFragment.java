package com.androidnerdcolony.didyouwork.pages.project.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.androidnerdcolony.didyouwork.data.DywContract;

/**
 * Fragment for Edit Project details.
 */

public class ProjectSettingsFragment extends Fragment {

    public static ProjectSettingsFragment newInstance(long projectId) {

        Bundle args = new Bundle();

        args.putLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, projectId);
        ProjectSettingsFragment fragment = new ProjectSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
