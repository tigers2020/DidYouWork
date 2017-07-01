package com.androidnerdcolony.didyouwork.pages.create_project;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
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
import com.androidnerdcolony.didyouwork.database.DywContract.DywEntries;
import com.androidnerdcolony.didyouwork.database.DywDataManager;
import com.androidnerdcolony.didyouwork.objects.WorkTimeObject;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateProjectActivity extends AppCompatActivity {

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
    @BindView(R.id.project_description)
    EditText descriptionView;
    Context context;
    boolean stringDel = false;
    Calendar c = Calendar.getInstance();


    List<String> tagList = new ArrayList<>();
    List<String> wageList = new ArrayList<>();
    ContentValues values;
    int wageType;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String formattedString = msg.getData().getString(getString(R.string.time_per_day), "0:00");
            projectTimePerDayView.setText(formattedString);
            projectTimePerDayView.setSelection(formattedString.length());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        context = this;
        values = DywDataManager.getDefaultProjectValue();

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
                values.put(DywEntries.COLUMN_PROJECT_NAME, s.toString());

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

                    String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    values.put(DywEntries.COLUMN_PROJECT_WAGE, parsed);

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
            Timer timer = new Timer();
            final int delay = 1000;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String cleanString = getCleanString(s.toString());
                WorkTimeObject workTime = ParseNumberToTimeObject(cleanString);
                String formatted =TimeObjectToStringFormatted(workTime);
                projectTimePerDayView.setText(formatted);
                projectTimePerDayView.setSelection(formatted.length());

            }

            private String getCleanString(String uncleanString) {
                String replaceable = "[^\\d.]";
                return uncleanString.replaceAll(replaceable, "");
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                if (!s.toString().equals(currentTime)) {
                    final String uncleanString = s.toString();
                    timer = new Timer();
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    String formatted = "";
                                    String cleanString = getCleanString(uncleanString);
                                    WorkTimeObject workTime = ParseNumberToTimeObject(cleanString);
                                    workTime = checkSixty(workTime);
                                    formatted = TimeObjectToStringFormatted(workTime);

                                    Message msg = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(getString(R.string.time_per_day), formatted);
                                    msg.setData(bundle);
                                    handler.sendMessage(msg);
                                    handler.obtainMessage(1).sendToTarget();
                                    Timber.d("formatted" + formatted);
                                    currentTime = formatted;

                                }

                                private WorkTimeObject checkSixty(WorkTimeObject workTime) {

                                    while (workTime.getTimeMinetues() > 59){
                                        workTime.setTimeHour(workTime.getTimeHour() + 1);
                                        workTime.setTimeMinetues(workTime.getTimeMinetues() - 60);
                                    }
                                    return workTime;
                                }

                            }, delay
                    );
                }
            }

            private WorkTimeObject ParseNumberToTimeObject(String cleanString) {
                double parsed;
                try {
                    parsed = Integer.parseInt(cleanString);
                } catch (NumberFormatException e) {
                    parsed = 0.00;
                }
                Timber.d("parsed" + parsed);

                WorkTimeObject workTime = new WorkTimeObject();

                workTime.setTimeHour((int) parsed / 100);
                workTime.setTimeMinetues((int) parsed % 100);
                long timeMillis = TimeUnit.HOURS.toMillis(workTime.getTimeHour()) + TimeUnit.MINUTES.toMillis(workTime.getTimeMinetues());
                values.put(DywEntries.COLUMN_PROJECT_WORK_TIME, timeMillis);

                return workTime;

            }
        });


        descriptionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                values.put(DywEntries.COLUMN_PROJECT_DESCRIPTION, s.toString());

            }
        });
        setTagClipper();
        setWageSpinner();


    }

    private String TimeObjectToStringFormatted(WorkTimeObject workTime) {
        return String.format(Locale.getDefault(), "%d:%02d", workTime.getTimeHour(), workTime.getTimeMinetues());
    }

    private void setWageSpinner() {
        wageTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                List<String> wageList = Arrays.asList(getResources().getStringArray(R.array.array_wage_type));
                String wageType;
                values.put(DywEntries.COLUMN_PROJECT_TYPE, position);
                if (position != 0) {
                    wageType = wageList.get(position);
                    if (position == 5) {
                        labelTimePerView.setText("Dead Line");
                    } else {
                        labelTimePerView.setText(wageType);
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
                Calendar c = Calendar.getInstance();
                long timeNow = c.getTimeInMillis();
                values.put(DywEntries.COLUMN_PROJECT_CREATED_DATE, timeNow);
                if (checkingValues()) {
                    createProject();
                } else {
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean checkingValues() {
        String projectName = values.getAsString(DywEntries.COLUMN_PROJECT_NAME);
        Double projectWage = values.getAsDouble(DywEntries.COLUMN_PROJECT_WAGE);
        long projectWorkTime = values.getAsLong(DywEntries.COLUMN_PROJECT_WORK_TIME);
        long projectDeadTIme = values.getAsLong(DywEntries.COLUMN_PROJECT_DEAD_LINE);
        String projectTags = values.getAsString(DywEntries.COLUMN_PROJECT_TAGS);
        String description = values.getAsString(DywEntries.COLUMN_PROJECT_DESCRIPTION);

        if (projectName.isEmpty() && TextUtils.equals(projectName, "")) {
            Toast.makeText(context, "You Must Enter the ProjectObject Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (projectWage == null && projectWage == 0.00) {
            Toast.makeText(context, "YOu Must Enter the ProjectObject Wage", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void createProject() {

        Uri uri = getContentResolver().insert(DywEntries.CONTENT_PROJECT_URI, values);
        if (uri != null) {
            getContentResolver().notifyChange(uri, null);
            Toast.makeText(context, "Project " + values.get(DywEntries.COLUMN_PROJECT_NAME) + " inserted : " + ContentUris.parseId(uri), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        projectTimePerDayView.setText(sdf.format(c.getTime()));
        values.put(DywEntries.COLUMN_PROJECT_DEAD_LINE, c.getTimeInMillis());
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
                    if (tagList.contains(tag)) {
                        Toast.makeText(context, "tag " + tag + " is already exist", Toast.LENGTH_SHORT).show();
                        tagsEditView.getText().clear();
                    } else {
                        addTag(tag);
                    }

                }

            }
        });

    }

    private void addTag(String tag) {
        tagList.add(tag);
        Gson gson = new Gson();

        String serializeTag = gson.toJson(tagList);
        Timber.d(serializeTag);
        values.put(DywEntries.COLUMN_PROJECT_TAGS, serializeTag);
        TextView textView = new TextView(context);
        textView.setText(tag);
        tagListView.addView(textView);
        tagsEditView.getText().clear();
        stringDel = false;
    }

    private void delTag(String tag) {
        tagList.remove(tag);
    }
}
