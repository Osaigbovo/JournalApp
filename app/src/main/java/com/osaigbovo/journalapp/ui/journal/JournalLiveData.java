package com.osaigbovo.journalapp.ui.journal;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JournalLiveData extends LiveData<DataSnapshot> {

    private static final String LOG_TAG = "JournalLiveData";
    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    public JournalLiveData(Query query) {
        this.query = query;
    }

    public JournalLiveData(DatabaseReference ref) {
        this.query = ref;
    }

    @Override
    protected void onActive() {
        query.addListenerForSingleValueEvent(listener);
    }

    @Override
    protected void onInactive() {
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //setValue(dataSnapshot);
            postValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
