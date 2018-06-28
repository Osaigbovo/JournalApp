package com.osaigbovo.journalapp.ui.journal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class JournalActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog picker;
    private TextView mTextViewDate, mTextVIewTime, mTextViewEntry,
            mTextViewLaugh, mTextViewHappy, mTextViewMeh, mTextViewSad, mTextViewCry;
    private ImageButton mImageButtonDate, mImageButtonTime;
    private FloatingActionButton mFab;

    private ImageButton[] btn = new ImageButton[5];
    private ImageButton btn_unfocus;
    private int[] btn_id
            = {R.id.image_laugh, R.id.image_happy, R.id.image_meh, R.id.image_sad, R.id.image_cry};

    private int mYear, mMonth, mDay, mHour, mMinute;

    private String stringDate, stringTime, stringEntry, stringEmotion, stringImage;

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
        mTextViewLaugh = findViewById(R.id.text_laugh);
        mTextViewHappy = findViewById(R.id.text_happy);
        mTextViewMeh = findViewById(R.id.text_meh);
        mTextViewSad = findViewById(R.id.text_sad);
        mTextViewCry = findViewById(R.id.text_cry);

        mImageButtonDate = findViewById(R.id.imagebutton_date);
        mImageButtonTime = findViewById(R.id.imagebutton_time);
        mFab = findViewById(R.id.fab_journal);

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

                //Toast.makeText(JournalActivity.this, stringEmotion + stringImage, Toast.LENGTH_LONG).show();
                getJournal();
            }
        });

        for (int i = 0; i < btn.length; i++) {
            btn[i] = findViewById(btn_id[i]);
            btn[i].setOnClickListener(this);
        }
        btn_unfocus = btn[0];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_laugh:
                setFocus(btn[0]);
                break;
            case R.id.image_happy:
                setFocus(btn[1]);
                break;
            case R.id.image_meh:
                setFocus(btn[2]);
                break;
            case R.id.image_sad:
                setFocus(btn[3]);
                break;
            case R.id.image_cry:
                setFocus(btn[4]);
                break;
        }
    }

    private void setFocus(ImageButton btn_focus) {

        if (btn_focus.getId() == R.id.image_laugh) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_laugh_selecteda));

            btn[1].setImageDrawable(getDrawable(R.drawable.ic_happy_normala));
            btn[2].setImageDrawable(getDrawable(R.drawable.ic_meh_normala));
            btn[3].setImageDrawable(getDrawable(R.drawable.ic_sad_normala));
            btn[4].setImageDrawable(getDrawable(R.drawable.ic_crying_normala));

            stringEmotion = mTextViewLaugh.getText().toString();
            stringImage = "R.drawable.ic_laugh_selecteda";

        } else if (btn_focus.getId() == R.id.image_happy) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_happy_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[2].setImageDrawable(getDrawable(R.drawable.ic_meh_normala));
            btn[3].setImageDrawable(getDrawable(R.drawable.ic_sad_normala));
            btn[4].setImageDrawable(getDrawable(R.drawable.ic_crying_normala));

            stringEmotion = mTextViewHappy.getText().toString();
            stringImage = "R.drawable.ic_happy_selecteda";

        } else if (btn_focus.getId() == R.id.image_meh) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_meh_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[1].setImageDrawable(getDrawable(R.drawable.ic_happy_normala));
            btn[3].setImageDrawable(getDrawable(R.drawable.ic_sad_normala));
            btn[4].setImageDrawable(getDrawable(R.drawable.ic_crying_normala));

            stringEmotion = mTextViewMeh.getText().toString();
            stringImage = "R.drawable.ic_meh_selecteda";

        } else if (btn_focus.getId() == R.id.image_sad) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_sad_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[1].setImageDrawable(getDrawable(R.drawable.ic_happy_normala));
            btn[2].setImageDrawable(getDrawable(R.drawable.ic_meh_normala));
            btn[4].setImageDrawable(getDrawable(R.drawable.ic_crying_normala));

            stringEmotion = mTextViewSad.getText().toString();
            stringImage = "R.drawable.ic_sad_selecteda";

        } else if (btn_focus.getId() == R.id.image_cry) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_crying_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[1].setImageDrawable(getDrawable(R.drawable.ic_happy_normala));
            btn[2].setImageDrawable(getDrawable(R.drawable.ic_meh_normala));
            btn[3].setImageDrawable(getDrawable(R.drawable.ic_sad_normala));

            stringEmotion = mTextViewCry.getText().toString();
            stringImage = "R.drawable.ic_crying_selecteda";
        }
        this.btn_unfocus = btn_focus;
    }

    private void getJournal() {

        stringDate = mTextViewDate.getText().toString();
        stringTime = mTextVIewTime.getText().toString();
        stringEntry = mTextViewEntry.getText().toString();
        //stringImage;
        //stringEmotion;

        if (TextUtils.isEmpty(stringDate)) {
            Toast.makeText(JournalActivity.this, "Select a date!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(stringTime)) {
            Toast.makeText(JournalActivity.this, "Select a Time!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(stringEmotion)) {
            Toast.makeText(JournalActivity.this, "Emoticon your day!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(stringEntry)) {
            Toast.makeText(JournalActivity.this, "How was your day?", Toast.LENGTH_LONG).show();
            return;
        }


    }
}
