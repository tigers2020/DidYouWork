package com.androidnerdcolony.didyouwork.pages.create_project.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnerdcolony.didyouwork.R;

import butterknife.ButterKnife;

/**
 * adapter for List of all Entries
 */

public class EntriesRecyclerAdapter extends RecyclerView.Adapter<EntriesRecyclerAdapter.ViewHolder> {

    private Context context;
    private Cursor mCursor;

    public EntriesRecyclerAdapter(Context context){
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entries, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mCursor != null) return mCursor.getCount();
        return 0;
    }

    public void SwapCursor(Cursor data) {
        mCursor = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
