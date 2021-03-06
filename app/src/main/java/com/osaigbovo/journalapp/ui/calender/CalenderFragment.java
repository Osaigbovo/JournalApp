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
package com.osaigbovo.journalapp.ui.calender;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.models.CalenderDates;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalenderFragment extends Fragment {

    private MCalendarView calendarView;
    private CalenderDates calenderDates;
    private ArrayList<CalenderDates> dates = new ArrayList<>();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CalenderViewModel calenderViewModel;

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
        calenderViewModel = ViewModelProviders.of(this, viewModelFactory).get(CalenderViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calender, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);

        LiveData<DataSnapshot> liveData = calenderViewModel.getDataSnapshotLiveData();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.RED);
                if (dataSnapshot != null) {
                    calenderDates = dataSnapshot.getValue(CalenderDates.class);
                    dates.add(calenderDates);
                    for (int i = 0; i < dates.size(); i++) {
                        calendarView.markDate(dates.get(i).getYear(), dates.get(i).getMonth(), dates.get(i).getDay())
                                .setMarkedStyle(MarkStyle.BACKGROUND, Color.RED);
                    }
                }
            }
        });

        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                Toast.makeText(getContext(), String.format("%d-%d", date.getMonth(), date.getDay()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        (getActivity()).setTitle("Calender");
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
