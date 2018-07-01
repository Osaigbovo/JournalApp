package com.osaigbovo.journalapp.ui.journal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osaigbovo.journalapp.data.models.CalenderDates;
import com.osaigbovo.journalapp.data.models.Journal;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class JournalViewModel extends ViewModel {

    public DatabaseReference JOURNAL_REF =
            FirebaseDatabase.getInstance().getReference().child("journal");

    private final JournalLiveData journalLiveData = new JournalLiveData(JOURNAL_REF);
    private final MediatorLiveData<Journal> mJournalLiveData = new MediatorLiveData<>();

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

    public void writeNewJournal(CalenderDates mCalenderDates, String stringDate, String stringTime,
                                String stringEntry, String stringImage, String stringEmotion) {

        String key = JOURNAL_REF.push().getKey();
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
}
