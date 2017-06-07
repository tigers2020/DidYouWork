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
import com.androidnerdcolony.didyouwork.database.DywContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            String dateString = dateFormat.format(new Date(dateLong));
            holder.entryDateView.setText(dateString);


            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.getDefault());
            String startTimeString = timeFormat.format(new Date(startTimeLong));
            String endTimeString = timeFormat.format(new Date(endTimeLong));

            holder.startTimeView.setText(startTimeString);
            holder.endTimeView.setText(endTimeString);

            long workTimeLong = endTimeLong - startTimeLong;

            SimpleDateFormat workTimeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

            String workTimeString = workTimeFormat.format(new Date(workTimeLong));

            holder.workTimeView.setText(workTimeString);

            holder.descriptionView.setText(description);


        } else {
            Toast.makeText(context, "no Entries Found", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
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
