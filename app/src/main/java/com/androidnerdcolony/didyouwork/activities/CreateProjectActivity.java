package com.androidnerdcolony.didyouwork.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_profit);
                dialog.setTitle("Fix Profit");
                final EditText profitInput = (EditText) dialog.findViewById(R.id.input_profit);
                Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
                Button btnDone = (Button) dialog.findViewById(R.id.btn_done);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(profitInput.getText())) {
                            projectProfitView.setText(profitInput.getText());
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "need input Profits", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
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
                    stringDel = s.charAt(s.length() - 1) == ';' || s.charAt(s.length() - 1) == ' ' || s.charAt(s.length() - 1) == ',' || s.charAt(s.length() - 1) == '.';
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (stringDel && s.length() != 0) {
                    TextView textView = new TextView(context);
                    String tag = s.toString();
                    tag = tag.subSequence(0, tag.length() - 1).toString();
                    textView.setText(tag);
                    tagListView.addView(textView);
                    tagsEditView.getText().clear();
                    stringDel = false;
                }

            }
        });

    }
}
