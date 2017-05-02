package com.androidnerdcolony.didyouwork;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.main_bottom_nav)
    BottomNavigationView bottomNavigationMenu;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        //// TODO: 5/1/2017 Design layouts

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_previous_project:
                        Toast.makeText(context, "previous project", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_add_project:
                        Toast.makeText(context, "create project", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_next_project:
                        Toast.makeText(context, "next project", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
}
