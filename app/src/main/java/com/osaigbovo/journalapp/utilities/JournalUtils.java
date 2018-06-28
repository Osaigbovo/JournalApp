package com.osaigbovo.journalapp.utilities;

import android.os.Build;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;

public class JournalUtils {

    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public static String getDate(String releaseDate) {

        if (TextUtils.isEmpty(releaseDate)) return "";

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate v = LocalDate.parse(releaseDate); // Require 26
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
                //String de = dateTimeFormatter.withLocale(Locale.getDefault()).format(v);
                return dateTimeFormatter.format(v);
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DATE_FORMAT.parse(releaseDate));

                return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "
                        + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
            }
        } catch (ParseException e) {
            return "";
        }
    }


}
