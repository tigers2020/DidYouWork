package com.androidnerdcolony.didyouwork.fragments.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.fragments.ProjectsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tiger on 5/4/2017.
 */

public class ProjectRecyclerAdapter extends RecyclerView.Adapter<ProjectRecyclerAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context context;

    public ProjectRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String projectName = mCursor.getString(ProjectsFragment.INDEX_PROJECT_NAME);
        String location = mCursor.getString(ProjectsFragment.INDEX_PROJECT_LOCATION);
        String type = mCursor.getString(ProjectsFragment.INDEX_PROJECT_TYPE);
        String wage = mCursor.getString(ProjectsFragment.INDEX_PROJECT_WAGE);
        String lastActivity = mCursor.getString(ProjectsFragment.INDEX_PROJECT_LAST_ACTIVITY);

        holder.projectNameView.setText(projectName);
        holder.locationView.setText(location);
        holder.projectTypeView.setText(type);
        holder.wageView.setText(wage);
        holder.lastActivityView.setText(lastActivity);


    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor data) {
        mCursor = data;
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.project_name)
        TextView projectNameView;
        @BindView(R.id.location)
        TextView locationView;
        @BindView(R.id.project_type)
        TextView projectTypeView;
        @BindView(R.id.wage)
        TextView wageView;
        @BindView(R.id.last_activity)
        TextView lastActivityView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
