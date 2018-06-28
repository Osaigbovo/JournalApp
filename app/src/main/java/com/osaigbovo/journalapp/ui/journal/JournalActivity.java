package com.osaigbovo.journalapp.ui.journal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.osaigbovo.journalapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class JournalActivity extends AppCompatActivity {

    private DatePickerDialog picker;
    private TextView mTextViewDate, mTextVIewTime, mTextViewEntry;
    private ImageButton mImageButtonDate, mImageButtonTime;
    private FloatingActionButton mFab;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        Toolbar toolbar = findViewById(R.id.toolbar_journal);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        mTextViewDate = findViewById(R.id.text_journal_date);
        mTextVIewTime = findViewById(R.id.text_journal_time);
        mTextViewEntry = findViewById(R.id.text_journal_entry);
        mImageButtonDate = findViewById(R.id.imagebutton_date);
        mImageButtonTime = findViewById(R.id.imagebutton_time);
        mFab = findViewById(R.id.fab_journal);

        //mImageButtonTime.isSelected();

        mImageButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                mDay = cldr.get(Calendar.DAY_OF_MONTH);
                mMonth = cldr.get(Calendar.MONTH);
                mYear = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(JournalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                cldr.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat simpleDateFormat
                                        = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

                                StringBuilder stringBuilder = new StringBuilder();

                                stringBuilder
                                        .append(cldr.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))
                                        .append(",  ")
                                        .append(cldr.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH))
                                        .append(" ")
                                        .append(cldr.get(Calendar.DAY_OF_MONTH))
                                        .append("   ")
                                        .append(cldr.get(Calendar.YEAR));

                                String s = stringBuilder.toString();

                                mTextViewDate.setText(s);
                            }

                        }, mYear, mMonth, mDay);
                picker.show();
            }
        });

        mImageButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(JournalActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                mTextVIewTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JournalActivity.this, "Submit Jounral Entry", Toast.LENGTH_LONG).show();
            }
        });
    }


}
