package com.androidnerdcolony.didyouwork;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.androidnerdcolony.didyouwork.fragments.EntriesFragment;
import com.androidnerdcolony.didyouwork.fragments.MoreFragment;
import com.androidnerdcolony.didyouwork.fragments.PeriodsFragment;
import com.androidnerdcolony.didyouwork.fragments.ProjectsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.navigation)
    TabLayout navigationView;
    @BindView(R.id.main_project_list)
    ViewPager projectListView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        //// TODO: 5/1/2017 Design layouts

        NaviFragmentAdapter adapter = new NaviFragmentAdapter(getSupportFragmentManager(), context);

        adapter.addFragment(ProjectsFragment.newIntance(), getString(R.string.project));
        adapter.addFragment(EntriesFragment.newIntance(), getString(R.string.entries));
        adapter.addFragment(PeriodsFragment.newIntance(), getString(R.string.periods));
        adapter.addFragment(MoreFragment.newIntance(), getString(R.string.more));

        projectListView.setAdapter(adapter);
        navigationView.setupWithViewPager(projectListView);

        navigationView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public class NaviFragmentAdapter extends FragmentPagerAdapter{

        List<Fragment> mFragmentList = new ArrayList<>();
        List<String> mFragmentTitleList = new ArrayList<>();

        private Context context;
        public NaviFragmentAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
