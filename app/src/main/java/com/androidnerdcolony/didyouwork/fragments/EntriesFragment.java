package com.androidnerdcolony.didyouwork.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnerdcolony.didyouwork.R;

/**
 * Created by tiger on 5/2/2017.
 */

public class EntriesFragment extends Fragment {

    public static EntriesFragment newIntance(){
        EntriesFragment fragment = new EntriesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entries, container, false);

        return view;
    }
}
