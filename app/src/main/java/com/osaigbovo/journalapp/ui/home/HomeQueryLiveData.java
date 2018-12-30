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
package com.osaigbovo.journalapp.ui.home;

import android.os.Handler;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.osaigbovo.journalapp.models.Home;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class HomeQueryLiveData extends LiveData<DataSnapshot> {

    private static final String LOG_TAG = "HomeQueryLiveData";

    private final Query query;
    private final ValueEventListener valueListener = new mValueEventListener();
    //private final ChildEventListener childListener = new MyEventListener();

    private List<Home> mQueryValuesList = new ArrayList<>();

    private boolean listenerRemovePending = false;
    private final Handler handler = new Handler();
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            query.removeEventListener(valueListener);
            listenerRemovePending = false;
        }
    };

    public HomeQueryLiveData(Query query) {
        this.query = query;
    }

    public HomeQueryLiveData(DatabaseReference dbReference) {
        this.query = dbReference;
    }

    @Override
    protected void onActive() {
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener);
        } else {
            query.addValueEventListener(valueListener);
        }
        listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }


    private class mValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Cannot listen to query " + query, databaseError.toException());
        }
    }

    private class MyEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (dataSnapshot != null) {
                Log.d(LOG_TAG, "onChildAdded(): previous child name = " + s);
                setValue(dataSnapshot);
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Home home = snap.getValue(Home.class);
                    mQueryValuesList.add(home);
                }
            } else {
                Log.w(LOG_TAG, "onChildAdded(): data snapshot is NULL");
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Cannot listen to query " + query, databaseError.toException());
        }
    }

}
