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
import com.androidnerdcolony.didyouwork.data.DywContract;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tiger on 6/2/2017.
 */

public class EntriesRecyclerAdapter extends RecyclerView.Adapter<EntriesRecyclerAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context context;

    public EntriesRecyclerAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entries, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            long dateLong = mCursor.getLong(DywContract.DywProjection.INDEX_PROJECT_CREATED_DATE);
            long startTimeLong = mCursor.getLong(DywContract.DywProjection.INDEX_ENTICES_START_DATE);
            long endTimeLong = mCursor.getLong(DywContract.DywProjection.INDEX_ENTRIES_END_DATE);
            String description = mCursor.getString(DywContract.DywProjection.INDEX_ENTRIES_DESCRIPTION);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(dateLong);

            String dateString = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);
            holder.entryDateView.setText(dateString);

            c.setTimeInMillis(startTimeLong);
            String startTimeString = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM);
            c.setTimeInMillis(endTimeLong);
            String endTimeString = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM);

            holder.startTimeView.setText(startTimeString);
            holder.endTimeView.setText(endTimeString);

            long workTimeLong = endTimeLong - startTimeLong;

            int workTimeHour = (int) (workTimeLong / 60 / 60 / 60);
            int workTimeMins = (int) (workTimeLong / 60 / 60);
            String workTimeString = workTimeHour + ":" + workTimeMins;

            holder.workTimeView.setText(workTimeString);

            holder.descriptionView.setText(description);


        } else {
            Toast.makeText(context, "no Entries Found", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void SwapCursor(Cursor cursor) {
        mCursor = cursor;
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
