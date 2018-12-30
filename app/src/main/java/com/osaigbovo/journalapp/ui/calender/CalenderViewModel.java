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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osaigbovo.journalapp.models.CalenderDates;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class CalenderViewModel extends ViewModel {

    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference CALENDER_REF =
            FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("dates");

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
