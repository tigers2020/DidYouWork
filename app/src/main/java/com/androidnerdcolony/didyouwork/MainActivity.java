package com.androidnerdcolony.didyouwork;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationMenu;
    @BindView(R.id.main_project_list)
    ViewPager projectListView;

    @BindView(R.id.fab_create_new_project)
    FloatingActionButton createNewProjectButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        //// TODO: 5/1/2017 Design layouts

        bottomNavigationMenu.inflateMenu(R.menu.menu_bottom_nav);
        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_project_list:
                        Toast.makeText(context, "project list", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_entries_list:
                        Toast.makeText(context, "entries list", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_periods_list:
                        Toast.makeText(context, "periods list", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_more_list:
                        Toast.makeText(context, "more list", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        createNewProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_create_project);
                dialog.setTitle("Create Project");

                dialog.show();
            }
        });
    }
}
