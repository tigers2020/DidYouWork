package com.androidnerdcolony.didyouwork.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.project_profit)
            TextView projectProfitView;
    @BindView(R.id.time_per_day)
            TextView projectTimePerDayView;

    Context context;
    boolean stringDel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        context = this;

        ButterKnife.bind(this);

        setTagClipper();
        setProjectProfitDialog();


    }

    private void setProjectProfitDialog() {
        projectProfitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "profit clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTagClipper() {
        tagsEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {
                    stringDel = s.charAt(s.length() - 1) == ';' || s.charAt(s.length() - 1) == ' ' || s.charAt(s.length() - 1) == ',' || s.charAt(s.length()-1) == '.';
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (stringDel && s.length() != 0) {
                    TextView textView = new TextView(context);
                    String tag = s.toString();
                    tag = tag.subSequence(0, tag.length()-1).toString();
                    textView.setText(tag);
                    tagListView.addView(textView);
                    tagsEditView.getText().clear();
                    stringDel = false;
                }

            }
        });

    }
}
