package com.androidnerdcolony.didyouwork.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.data.DywContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateProjectActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

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
    @BindView(R.id.wage_type)
    Spinner wageTypeSpinner;
    @BindView(R.id.label_project_time_per_day)
            TextView labelTimePerView;
    Context context;
    boolean stringDel = false;
    Calendar c = Calendar.getInstance();


    List<String> tagList = new ArrayList<>();
    List<String> wageList = new ArrayList<>();
    ContentValues values;
    int wageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        context = this;

        ButterKnife.bind(this);

        projectNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                values.put(DywContract.DywEntries.COLUMN_PROJECT_NAME, s.toString());

            }
        });

        setTagClipper();
        setProjectProfitDialog();
        setwageSpinner();


    }

    private void setwageSpinner() {
        wageTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                String temp;
                values.put(DywContract.DywEntries.COLUMN_PROJECT_TYPE, position);
                wageType = position;
                if (position != 0) {
                    labelTimePerView.setText(String.valueOf(parent.getItemAtPosition(position)));

                }
                if (position == 5){
                    projectTimePerDayView.setText("Dead Line");
                }else {
                    projectTimePerDayView.setText("00:00");
                }

                projectTimePerDayView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 5) {
                            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    c.set(Calendar.YEAR, year);
                                    c.set(Calendar.MONTH, month);
                                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    updateDate();

                                }


                            };
                            new DatePickerDialog(context, date, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                        }else{
                            Dialog dialog = new Dialog(context);
                            dialog.setTitle("set Wage");
                            dialog.setContentView(R.layout.dialog_wage);
                            NumberPicker hourPick = (NumberPicker) dialog.findViewById(R.id.number_pick_hour);
                            NumberPicker minsPick = (NumberPicker) dialog.findViewById(R.id.number_pick_mins);


                            dialog.show();

                            hourPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                                @Override
                                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                }
                            });


                        }

                    }
                });
//                switch (position){
//                    case 1:
//                        temp = "Hourly paid";
//                        projectTimePerDayView.setText("$00.00");
//                        break;
//                    case 2:
//                        temp = "Weekly paid";
//                        projectTimePerDayView.setText("$00.00");
//                        break;
//                    case 3:
//                        temp = "Monthly paid";
//                        projectTimePerDayView.setText("$00.00");
//                        break;
//                    case 4:
//                        temp = "Yearly paid";
//                        projectTimePerDayView.setText("$00.00");
//                        break;
//                    case 5:
//                        temp = "Dead line";
//                        projectTimePerDayView.setText("Dead Line");
//
//                        break;
//                    default:
//                        temp = "None Selected";
//                        projectTimePerDayView.setText("Select Payment Type");
//                        break;
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_cancel:
                finish();
                return true;
            case R.id.action_done:

                return true;
            default:
                return false;
        }
    }

    private void updateDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        projectTimePerDayView.setText(sdf.format(c.getTime()));
        values.put(DywContract.DywEntries.COLUMN_PROJECT_DEAD_LINE, c.getTimeInMillis());
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

                final String profitString = profitInput.getText().toString();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(profitString)) {
                            projectProfitView.setText(profitString);
                            int wage = Integer.valueOf(profitString);
                            values.put(DywContract.DywEntries.COLUMN_PROJECT_WAGE, wage);
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
                    String tag = s.toString();
                    tag = tag.subSequence(0, tag.length() - 1).toString();

                    addTag(tag);

                }

            }
        });

    }

    private void addTag(String tag) {
        tagList.add(tag);
        TextView textView = new TextView(context);
        textView.setText(tag);
        tagListView.addView(textView);
        tagsEditView.getText().clear();
        stringDel = false;
    }

    private void delTag(String tag) {
        tagList.remove(tag);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
