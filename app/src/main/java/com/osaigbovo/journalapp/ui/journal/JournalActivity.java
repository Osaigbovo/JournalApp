package com.osaigbovo.journalapp.ui.journal;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.osaigbovo.journalapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class JournalActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private DatePickerDialog picker;
    private ImageButton mImageButton;
    private TextView mTextView;

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

        mTextView = findViewById(R.id.text_journal_date);
        mImageButton = findViewById(R.id.imagebutton_date);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
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

                                mTextView.setText(s);
                            }

                        }, year, month, day);
                picker.show();
            }
        });

    }

}
