package com.androidnerdcolony.didyouwork.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
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
import com.androidnerdcolony.didyouwork.data.DywContract;
import com.androidnerdcolony.didyouwork.fragments.EntriesFragment;
import com.androidnerdcolony.didyouwork.fragments.MoreFragment;
import com.androidnerdcolony.didyouwork.fragments.ProjectsFragment;

import java.util.Calendar;

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

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = ProjectsFragment.newInstance();

        fm.beginTransaction().replace(R.id.main_content, fragment, fragment.getTag()).commit();

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

        Fragment fragment = fragment = ProjectsFragment.newInstance();

        FragmentManager fm = getSupportFragmentManager();
        switch (item.getItemId())
        {
            case R.id.action_add_temp_project:
                ContentValues values = new ContentValues();

                values.put(DywContract.DywEntries.COLUMN_PROJECT_NAME, "test name");
                values.put(DywContract.DywEntries.COLUMN_PROJECT_WAGE, 24.50);
                values.put(DywContract.DywEntries.COLUMN_PROJECT_LOCATION, "[24, 52]");
                long time = Calendar.getInstance().getTimeInMillis();
                values.put(DywContract.DywEntries.COLUMN_PROJECT_CREATED_DATE, time);
                values.put(DywContract.DywEntries.COLUMN_ENTRIES_TAGS, "android, tag, etc");
                long deadline = time + 13600;
                values.put(DywContract.DywEntries.COLUMN_PROJECT_DEAD_LINE, deadline);
                values.put(DywContract.DywEntries.COLUMN_PROJECT_TIME_ROUNDING, 30);
                values.put(DywContract.DywEntries.COLUMN_PROJECT_TYPE, 1);
                values.put(DywContract.DywEntries.COLUMN_PROJECT_DESCRIPTION, "test project is long long description.\ntest project is long long description.\ntest project is long long description.\ntest project is long long description.\ntest project is long long description.\n");
                Uri uri = getContentResolver().insert(DywContract.DywEntries.CONTENT_PROJECT_URI, values);
                long id;
                if (uri != null){
                    id = ContentUris.parseId(uri);
                    Toast.makeText(context, "Data been Added " + id, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_add_temp_entries:
                ContentValues entryValue = new ContentValues();
                entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, 1);
                long startTime = Calendar.getInstance().getTimeInMillis();
                long endTime = startTime + 23800;
                entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_START_DATE, startTime);
                entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_END_DATE, endTime);
                entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_TAGS, "");
                entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_OVER_TIME, 8*60*60);
                entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_BONUS, 35.50);
                entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_DESCRIPTION, "test Entries long long description \ntest Entries long long description \ntest Entries long long description \ntest Entries long long description \ntest Entries long long description \ntest Entries long long description \n");
                Uri entryUri = getContentResolver().insert(DywContract.DywEntries.CONTENT_ENTRIES_URI, entryValue);
                long entryId;
                if (entryUri != null){
                    entryId = ContentUris.parseId(entryUri);
                    Toast.makeText(context, "Entry been Added " + entryId, Toast.LENGTH_SHORT).show();
                }
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
