package com.androidnerdcolony.didyouwork.pages.project.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.data.EntryDataStructure;
import com.androidnerdcolony.didyouwork.data.ProjectDataStructure;
import com.androidnerdcolony.didyouwork.database.DywContract;
import com.androidnerdcolony.didyouwork.database.DywDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;



public class EntriesRecyclerAdapter extends RecyclerView.Adapter<EntriesRecyclerAdapter.ViewHolder> {

    private Cursor entriesCursor;
    private Cursor projectCursor;

    private ProjectDataStructure projectDataStructure;
    private EntryDataStructure entryDataStructure;
    private Context context;

    public EntriesRecyclerAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entries, parent, false);
        ViewHolder vh = new ViewHolder(view);
        projectDataStructure = new ProjectDataStructure();
        entryDataStructure = new EntryDataStructure();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (projectCursor != null) {
            if (projectCursor.moveToFirst()) {
                projectDataStructure = DywDataManager.ConvertToProjectData(projectCursor);
                double hourlyWage = projectCursor.getDouble(DywContract.DywProjection.INDEX_PROJECT_WAGE);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                long dateLong = projectCursor.getLong(DywContract.DywProjection.INDEX_PROJECT_CREATED_DATE);

                String dateString = dateFormat.format(new Date(dateLong));
                holder.entryDateView.setText(dateString);


            }
            
        }
        if (entriesCursor.moveToPosition(position)) {
            entryDataStructure = DywDataManager.ConvertToEntryData(entriesCursor, position);
            long startTimeLong = entriesCursor.getLong(DywContract.DywProjection.INDEX_ENTICES_START_DATE);
            long endTimeLong = entriesCursor.getLong(DywContract.DywProjection.INDEX_ENTRIES_END_DATE);
            String description = entriesCursor.getString(DywContract.DywProjection.INDEX_ENTRIES_DESCRIPTION);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.getDefault());
            String startTimeString = timeFormat.format(new Date(startTimeLong));
            String endTimeString = timeFormat.format(new Date(endTimeLong));

            holder.startTimeView.setText(startTimeString);
            holder.endTimeView.setText(endTimeString);

            long workTimeLong = endTimeLong - startTimeLong;

            SimpleDateFormat workTimeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

            String workTimeString = getWorkTimeStringFormat(workTimeLong);

            holder.workTimeView.setText(workTimeString);

            holder.descriptionView.setText(description);


        } else {
            Toast.makeText(context, "no Entries Found", Toast.LENGTH_SHORT).show();
        }


    }

    private String getWorkTimeStringFormat(long workTimeLong) {

        int hour = (int) workTimeLong / (60 * 60 * 1000) % 60;
        int minutes = (int) (workTimeLong / (60 * 1000)) % 60;
        int seconds = (int) (workTimeLong / 1000) % 60;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minutes, seconds);
    }

    @Override
    public int getItemCount() {
        if (entriesCursor == null) return 0;
        return entriesCursor.getCount();
    }

    public void SwapEntriesCursor(Cursor cursor) {
        entriesCursor = cursor;
        notifyDataSetChanged();
    }

    public void SwapProjectCursor(Cursor cursor) {
        projectCursor = cursor;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.entry_date)
        TextView entryDateView;
        @BindView(R.id.start_time)
        TextView startTimeView;
        @BindView(R.id.end_time)
        TextView endTimeView;
        @BindView(R.id.wage_earned)
        TextView wageEarnedView;
        @BindView(R.id.work_time)
        TextView workTimeView;
        @BindView(R.id.description)
        TextView descriptionView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
