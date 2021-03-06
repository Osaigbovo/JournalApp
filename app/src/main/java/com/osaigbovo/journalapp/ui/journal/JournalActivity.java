/*
 * Copyright 2018.  Osaigbovo Odiase
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.osaigbovo.journalapp.ui.journal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.osaigbovo.journalapp.MainActivity;
import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.models.CalenderDates;
import com.osaigbovo.journalapp.models.Home;
import com.osaigbovo.journalapp.models.Journal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

@SuppressWarnings("SpellCheckingInspection")
public class JournalActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_JOURNAL_KEY = "journal_key";
    private String mJournalKey;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private JournalViewModel journalViewModel;

    private final Calendar calendar = Calendar.getInstance();

    private TextView mTextViewDate, mTextVIewTime, mTextViewEntry,
            mTextViewLaugh, mTextViewHappy, mTextViewMeh, mTextViewSad, mTextViewCry;
    private ImageButton mImageButtonDate, mImageButtonTime;
    private FloatingActionButton mFab;

    private ImageButton[] btn = new ImageButton[5];
    private ImageButton btn_unfocus;
    private int[] btn_id
            = {R.id.image_laugh, R.id.image_happy, R.id.image_meh, R.id.image_sad, R.id.image_cry};

    private int mYearB, mMonthB, mDayB;
    private String stringDate, stringTime, stringEntry, stringEmotion, stringImage;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        journalViewModel
                = ViewModelProviders.of(this, viewModelFactory).get(JournalViewModel.class);

        initView();

        intent = getIntent();
        if (intent.hasExtra("Edit")) {
            setTitle("Edit Journal");

            Home h = intent.getParcelableExtra("Edit");
            Log.e("ASDFGHJKL;", h.toString());
            mTextViewDate.setText(h.getDate());
            mTextVIewTime.setText(h.getTime());
            mTextViewEntry.setText(h.getEntry());
        } else {

        }

        // Get post key from intent
        mJournalKey = getIntent().getStringExtra(EXTRA_JOURNAL_KEY);
        if (mJournalKey != null) {
            throw new IllegalArgumentException("Must pass EXTRA_JOURNAL_KEY");
        }

        mImageButtonDate.setOnClickListener((View v) -> getDate());
        mImageButtonTime.setOnClickListener((View v) -> getTime());
        mFab.setOnClickListener((View v) -> getJournal());

        for (int i = 0; i < btn.length; i++) {
            btn[i] = findViewById(btn_id[i]);
            btn[i].setOnClickListener(this);
        }
        btn_unfocus = btn[0];
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_journal);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar(); // Get a support ActionBar corresponding to this toolbar
        ab.setDisplayHomeAsUpEnabled(true); // Enable the Up button

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
            stringImage = "R.drawable.ic_laugh_selecteda.png";

        } else if (btn_focus.getId() == R.id.image_happy) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_happy_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[2].setImageDrawable(getDrawable(R.drawable.ic_meh_normala));
            btn[3].setImageDrawable(getDrawable(R.drawable.ic_sad_normala));
            btn[4].setImageDrawable(getDrawable(R.drawable.ic_crying_normala));

            stringEmotion = mTextViewHappy.getText().toString();
            stringImage = "R.drawable.ic_happy_selecteda.png";

        } else if (btn_focus.getId() == R.id.image_meh) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_meh_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[1].setImageDrawable(getDrawable(R.drawable.ic_happy_normala));
            btn[3].setImageDrawable(getDrawable(R.drawable.ic_sad_normala));
            btn[4].setImageDrawable(getDrawable(R.drawable.ic_crying_normala));

            stringEmotion = mTextViewMeh.getText().toString();
            stringImage = "R.drawable.ic_meh_selecteda.png";

        } else if (btn_focus.getId() == R.id.image_sad) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_sad_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[1].setImageDrawable(getDrawable(R.drawable.ic_happy_normala));
            btn[2].setImageDrawable(getDrawable(R.drawable.ic_meh_normala));
            btn[4].setImageDrawable(getDrawable(R.drawable.ic_crying_normala));

            stringEmotion = mTextViewSad.getText().toString();
            stringImage = "R.drawable.ic_sad_selecteda.png";

        } else if (btn_focus.getId() == R.id.image_cry) {
            btn_focus.setImageDrawable(getDrawable(R.drawable.ic_crying_selecteda));

            btn[0].setImageDrawable(getDrawable(R.drawable.ic_laugh_normala));
            btn[1].setImageDrawable(getDrawable(R.drawable.ic_happy_normala));
            btn[2].setImageDrawable(getDrawable(R.drawable.ic_meh_normala));
            btn[3].setImageDrawable(getDrawable(R.drawable.ic_sad_normala));

            stringEmotion = mTextViewCry.getText().toString();
            stringImage = "R.drawable.ic_crying_selecteda.png";
        }
        this.btn_unfocus = btn_focus;
    }

    private void getJournal() {

        stringDate = mTextViewDate.getText().toString();
        stringTime = mTextVIewTime.getText().toString();
        stringEntry = mTextViewEntry.getText().toString();

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

        LiveData<DataSnapshot> journalLiveData = journalViewModel.getDataSnapshotLiveData();
        journalLiveData.observe(this, (DataSnapshot dataSnapshot) -> {
            if (dataSnapshot != null) {
                CalenderDates mCalenderDates = new CalenderDates(mYearB, mMonthB, mDayB);
                Journal mJournal = new Journal(stringDate, stringTime, stringEntry, stringImage, stringEmotion);

                journalViewModel.writeNewJournal(mCalenderDates, mJournal);

                Toast.makeText(JournalActivity.this, "Journal...",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(JournalActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (intent.hasExtra("Edit")) {
            setTitle("Edit Journal");

            Home h = intent.getParcelableExtra("Edit");

            mTextViewDate.setText(h.getDate());
            mTextVIewTime.setText(h.getTime());
            mTextViewEntry.setText(h.getEntry());
        } else {
            setTitle("Add Journal");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void getDate() {
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mMonth = calendar.get(Calendar.MONTH);
        int mYear = calendar.get(Calendar.YEAR);

        // date picker dialog
        DatePickerDialog picker
                = new DatePickerDialog(JournalActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(year, monthOfYear, dayOfMonth);
            mYearB = year;
            mMonthB = Integer.parseInt(String.valueOf(monthOfYear), 8) + 1;
            mDayB = dayOfMonth;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
            mTextViewDate.setText(simpleDateFormat.format(calendar.getTime()));
        }, mYear, mMonth, mDay);

        picker.show();
    }

    private void getTime() {
        // Get Current Time
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(JournalActivity.this,
                (view, hourOfDay, minute) -> mTextVIewTime.setText(String.valueOf(hourOfDay + ":" + minute)),
                mHour, mMinute, false);

        timePickerDialog.show();
    }

}
