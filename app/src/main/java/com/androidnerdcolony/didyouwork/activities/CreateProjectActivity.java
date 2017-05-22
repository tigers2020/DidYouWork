package com.androidnerdcolony.didyouwork.activities;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnerdcolony.didyouwork.R;
import com.androidnerdcolony.didyouwork.data.DywContract;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateProjectActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.project_name)
    EditText projectNameView;
    @BindView(R.id.tags)
    EditText tagsEditView;
    @BindView(R.id.tag_list)
    LinearLayout tagListView;
    @BindView(R.id.project_profit)
    EditText projectWageView;
    @BindView(R.id.time_per_day)
    EditText projectTimePerDayView;
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
        values = new ContentValues();

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
        projectWageView.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    projectWageView.removeTextChangedListener(this);

                    String replaceable = String.format("[$s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted;
                    projectWageView.setText(formatted);
                    projectWageView.setSelection(formatted.length());
                    projectWageView.addTextChangedListener(this);
                }

            }
        });
        projectTimePerDayView.addTextChangedListener(new TextWatcher() {
            String currentTime = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().equals(currentTime)) {
                    projectTimePerDayView.removeTextChangedListener(this);
                    Timber.d("original" + s.toString());
                    String replaceable = "[^\\d.]";
                    String cleanString = s.toString().replaceAll(replaceable, "");
                    Timber.d("cleaned" + cleanString);

                    double parsed;
                    try {
                        parsed = Integer.parseInt(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    Timber.d("parsed" + parsed);
                    int hour = (int) parsed / 100;
                    int min = (int) parsed - (hour * 100);

                    if (parsed > 100) {
                        if (min > 59) {
                            hour = hour + (min / 60);
                            min = min % 60;
                        }

                        if (hour > 24) {
                            hour = 24;
                            Toast.makeText(context, "You can't work more than 24 hours per day", Toast.LENGTH_SHORT).show();
                        }
                    }

                    String formatted = String.format("%d:%02d", hour, min);
                    Timber.d("formatted" + formatted);
                    currentTime = formatted;
                    projectTimePerDayView.setText(currentTime);
                    projectTimePerDayView.setSelection(formatted.length());
                    projectTimePerDayView.addTextChangedListener(this);
                }
            }
        });

        setTagClipper();
        setWageSpinner();


    }

    private void setWageSpinner() {
        wageTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                String temp;
                values.put(DywContract.DywEntries.COLUMN_PROJECT_TYPE, position);
                String timeString = "";
                if (position != 0) {
                    switch (position) {
                        case 1:
                            timeString = " / Per Hour";
                            break;
                        case 2:
                            timeString = " / Per Week";
                            break;
                        case 3:
                            timeString = " / Per Month";
                            break;
                        case 4:
                            timeString = " / Per year";
                            break;
                        case 5:
                            timeString = " / Per Project";
                            break;
                        default:
                            timeString = "";
                            break;
                    }
                    if (position == 5) {
                        timeString = "Dead Line";
                        projectTimePerDayView.setText("Dead Line");
                    } else {
                        projectTimePerDayView.setText("00:00" + timeString);
                    }
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


                        }

                    }
                });
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
        switch (item.getItemId()) {
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
