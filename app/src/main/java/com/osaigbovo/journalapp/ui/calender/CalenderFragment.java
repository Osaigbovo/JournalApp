package com.osaigbovo.journalapp.ui.calender;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.osaigbovo.journalapp.R;

import java.util.ArrayList;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalenderFragment extends Fragment {

    private MCalendarView calendarView;

    private OnFragmentInteractionListener mListener;

    public CalenderFragment() {
        // Required empty public constructor
    }

    public static CalenderFragment newInstance() {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calender, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);

        ArrayList<DateData> dates = new ArrayList<>();
        dates.add(new DateData(2018, 04, 26));
        dates.add(new DateData(2018, 04, 27));

        // Mark multiple dates with this code.
        for (int i = 0; i < dates.size(); i++) {
            calendarView
                    .markDate(dates.get(i).getYear(), dates.get(i).getMonth(), dates.get(i).getDay());

            /*mCalendarView
                    .markDate(new DateData(2016, 3, 1)
                            .setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.GREEN)));*/
        }
        // Get all marked dates.
        Log.d("marked dates:-", "" + calendarView.getMarkedDates());

        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                Toast.makeText(getContext(), String.format("%d-%d", date.getMonth(), date.getDay()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
