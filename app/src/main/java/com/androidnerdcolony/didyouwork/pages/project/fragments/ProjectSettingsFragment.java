package com.androidnerdcolony.didyouwork.pages.project.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectId = getArguments().getLong(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_project, container, false);
        unbinder = ButterKnife.bind(this, view);

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
        projectTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        projectTypeSpinner.setAdapter(projectTypeAdapter);


        projectWageView.addTextChangedListener(new TextWatcher() {
            String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {

                    projectWageView.removeTextChangedListener(this);
                    String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted;

                    projectWageView.setText(formatted);
                    projectWageView.setSelection(formatted.length());
                    projectWageView.addTextChangedListener(this);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
            String workTime = "";
            if (projectData != null) {
                projectName = projectData.getProject_name();
                projectWage = String.valueOf(projectData.getWage());
                projectType = projectData.getProject_type();
                workTime = convertToHour(projectData.getWork_time());
                duration = convertToDate(projectData.getProject_duration());
                description = projectData.getDescription();
            }
            projectNameView.setText(projectName);
            projectWageView.setText(projectWage);
            workTimeView.setText(workTime);
            projectTypeSpinner.setSelection(projectType);
            projectDurationView.setText(duration);
            descriptionView.setText(description);


        }

    }

    private String convertToHour(long work_time) {
        String timeString;

        long hour = TimeUnit.MILLISECONDS.toHours(work_time);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(work_time);

        timeString = String.format(Locale.getDefault(), "%d:%02d", hour, minutes);

        return timeString;
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
