package com.androidnerdcolony.didyouwork.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnerdcolony.didyouwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateProjectActivity extends AppCompatActivity {

    @BindView(R.id.project_name)
    EditText projectNameView;
    @BindView(R.id.tags)
    EditText tagsEditView;
    @BindView(R.id.tag_list)
    LinearLayout tagListView;

    Context context;
    boolean stringDel = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        context = this;

        ButterKnife.bind(this);

        tagsEditView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_COMMA | keyCode == KeyEvent.KEYCODE_SEMICOLON | keyCode == KeyEvent.KEYCODE_SPACE)
                {
                    stringDel = true;
                }else {
                    stringDel = false;
                }
                return false;
            }

        });
        tagsEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (stringDel){
                    TextView textView = new TextView(context);
                    textView.setText(s);
                    tagListView.addView(textView);
                    tagsEditView.setText("");
                    stringDel = false;
                }
            }
        });


    }
}
