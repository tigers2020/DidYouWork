package com.androidnerdcolony.didyouwork.pages.project.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.data.ProjectDataStructure;
import com.androidnerdcolony.didyouwork.database.DywContract;
import com.androidnerdcolony.didyouwork.database.DywDataManager;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment for Edit ProjectDataStructure details.
 */

public class ProjectSettingsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final static int PROJECT_LOADER = 101;
    long projectId = -1;
    Unbinder unbinder;
    @BindView(R.id.project_name)
    EditText projectNameView;
    @BindView(R.id.project_wage)
    EditText projectWageView;
    @BindView(R.id.project_type)
    Spinner projectTypeSpinner;
    @BindView(R.id.project_duration)
    EditText projectDurationView;
    @BindView(R.id.work_time)
    EditText workTimeView;
    @BindView(R.id.tags)
    EditText tagsView;
    @BindView(R.id.layout_tags)
    LinearLayout tagListView;
    @BindView(R.id.description)
    EditText descriptionView;

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
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            projectId = savedInstanceState.getLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, -1);
        }

        if (projectId != -1) {
            LoaderManager loaderManager = getLoaderManager();

            Loader<Cursor> projectLoader = loaderManager.getLoader(PROJECT_LOADER);

            if (projectLoader == null) {
                loaderManager.initLoader(PROJECT_LOADER, null, this);
            } else {
                loaderManager.restartLoader(PROJECT_LOADER, savedInstanceState, this);
            }
        }
        String[] projectTypeArray = getResources().getStringArray(R.array.array_wage_type);
        List<String> projectTypeArrayList = Arrays.asList(projectTypeArray);
        ArrayAdapter<String> projectTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, projectTypeArrayList);
        projectTypeSpinner.setAdapter(projectTypeAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        if (data.moveToFirst()){
            ProjectDataStructure projectData = DywDataManager.ConvertToProjectData(data);

            String projectName = "";
            String projectWage = "";
            int projectType = 0;
            String duration = "";
            String description = "";
            if (projectData != null) {
                projectName = projectData.getProject_name();
                projectWage = String.valueOf(projectData.getWage());
                projectType = projectData.getProject_type();
                duration = convertToDate(projectData.getProject_duration());
                description = projectData.getDescription();
            }
            projectNameView.setText(projectName);
            projectWageView.setText(projectWage);
            projectTypeSpinner.setSelection(projectType);
            projectDurationView.setText(duration);
            descriptionView.setText(description);


        }

    }

    private String convertToTypeString(int project_type) {

        List<String> arrayList = Arrays.asList(getResources().getStringArray(R.array.array_wage_type));

        return arrayList.get(project_type);
    }

    private String convertToDate(long project_duration) {
        String dateString;

        Date date = new Date(project_duration);

        dateString = date.toString();

        return dateString;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
