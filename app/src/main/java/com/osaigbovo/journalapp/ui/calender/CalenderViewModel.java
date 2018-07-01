package com.osaigbovo.journalapp.ui.calender;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osaigbovo.journalapp.models.CalenderDates;

import javax.inject.Inject;

public class CalenderViewModel extends ViewModel {

    private static final DatabaseReference CALENDER_REF =
            FirebaseDatabase.getInstance().getReference().child("journal").child("dates");

    private final CalenderLiveData liveData = new CalenderLiveData(CALENDER_REF);

    private final MediatorLiveData<CalenderDates> mCalenderLiveData = new MediatorLiveData<>();

    @Inject
    public CalenderViewModel() {
        // Set up the MediatorLiveData to convert DataSnapshot objects into CalenderDates objects
        mCalenderLiveData.addSource(liveData, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mCalenderLiveData.postValue(dataSnapshot.getValue(CalenderDates.class));
                        }
                    }).start();
                } else {
                    mCalenderLiveData.setValue(null);
                }
            }
        });
    }

    private final LiveData<CalenderDates> calenderLiveData =
            Transformations.map(liveData, new Deserializer());

    private class Deserializer implements Function<DataSnapshot, CalenderDates> {
        @Override
        public CalenderDates apply(DataSnapshot dataSnapshot) {
            return dataSnapshot.getValue(CalenderDates.class);
        }
    }

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}
