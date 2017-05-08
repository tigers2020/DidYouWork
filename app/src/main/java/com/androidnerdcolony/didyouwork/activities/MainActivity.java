package com.androidnerdcolony.didyouwork.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.fragments.EntriesFragment;
import com.androidnerdcolony.didyouwork.fragments.MoreFragment;
import com.androidnerdcolony.didyouwork.fragments.ProjectsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
            NavigationView navigationView;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        //// TODO: 5/1/2017 Design layouts
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = new Intent(this, CreateProjectActivity.class);
        startActivity(intent);





    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        switch (item.getItemId())
        {
            case R.id.action_list_of_project:
                Toast.makeText(context, "List of Project", Toast.LENGTH_SHORT).show();
                fragment = ProjectsFragment.newInstance();
                break;
            case R.id.action_entries_list:
                Toast.makeText(context, "List of Entries", Toast.LENGTH_SHORT).show();
                fragment = EntriesFragment.newInstance();
                break;
            case R.id.action_settings:
                Toast.makeText(context, "List of Settings", Toast.LENGTH_SHORT).show();
                fragment = MoreFragment.newInstance();
                break;
            default:
                fragment = ProjectsFragment.newInstance();
                break;
        }
        fm.beginTransaction().replace(R.id.main_content, fragment, fragment.getTag()).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
