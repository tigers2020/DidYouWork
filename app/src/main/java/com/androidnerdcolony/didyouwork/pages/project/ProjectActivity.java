package com.androidnerdcolony.didyouwork.pages.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.data.DywContract;
import com.androidnerdcolony.didyouwork.pages.project.fragments.ProjectEntriesFragment;
import com.androidnerdcolony.didyouwork.pages.project.fragments.ProjectSettingsFragment;
import com.androidnerdcolony.didyouwork.pages.project.fragments.ProjectStatsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


    @BindView(R.id.tab_layout)
    TabLayout tabLayoutView;
    @BindView(R.id.container)
    ViewPager mViewPager;

    Fragment tabFragment;

    long projectId;
    Context context;

    int PROJECT_LOADER = 11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        context = this;

        Intent intent = getIntent();

        projectId = intent.getLongExtra(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, -1);

        ProjectPageAdapter adapter = new ProjectPageAdapter(getSupportFragmentManager());

        adapter.AddFragment(ProjectStatsFragment.newInstance(projectId), "Project");
        adapter.AddFragment(ProjectEntriesFragment.newInstance(projectId), "Entries");
        adapter.AddFragment(ProjectSettingsFragment.newInstance(projectId), "Settings");
        mViewPager.setAdapter(adapter);

        tabLayoutView.setupWithViewPager(mViewPager);

        tabLayoutView.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private class ProjectPageAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        ProjectPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void AddFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }
    }
}
