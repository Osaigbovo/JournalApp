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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osaigbovo.journalapp.models.CalenderDates;
import com.osaigbovo.journalapp.models.Home;
import com.osaigbovo.journalapp.models.Journal;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class JournalViewModel extends ViewModel {

    public DatabaseReference JOURNAL_REF =
            FirebaseDatabase.getInstance().getReference().child("users");

    private final JournalLiveData journalLiveData = new JournalLiveData(JOURNAL_REF);
    private final MediatorLiveData<Journal> mJournalLiveData = new MediatorLiveData<>();
    private LiveData<Journal> search;
    private LiveData<DataSnapshot> retrieving;

    @Inject
    public JournalViewModel() {
        mJournalLiveData.addSource(journalLiveData, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mJournalLiveData.postValue(dataSnapshot.getValue(Journal.class));
                        }
                    }).start();
                } else {
                    mJournalLiveData.setValue(null);
                }
            }
        });

    }

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return journalLiveData;
    }

    public LiveData<Journal> getJ() {
        return search;
    }

    public void writeNewJournal(CalenderDates mCalenderDates, String userId, String stringDate,
                                String stringTime, String stringEntry, String stringImage,
                                String stringEmotion) {

        String key = JOURNAL_REF.child(userId).push().getKey();
        Journal mJournal = new Journal(stringDate, stringTime, stringEntry, stringImage, stringEmotion);

        Map<String, Object> postValues = mJournal.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/journals/" + key, postValues);
        Map<String, Object> postDateValues = mCalenderDates.toMap();
        Map<String, Object> childDateUpdates = new HashMap<>();
        childDateUpdates.put("/dates/" + key, postDateValues);

        JOURNAL_REF.updateChildren(childUpdates);
        JOURNAL_REF.updateChildren(childDateUpdates);
    }

    public void getJournal(Home home) {
        JOURNAL_REF.orderByChild("entry")
                .equalTo(home.getEntry())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String clubkey = childSnapshot.getKey();

                            Home h = dataSnapshot.child(clubkey).getValue(Home.class);
                            /*dataRef.getParent().child("dates").child(clubkey).removeValue();*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
