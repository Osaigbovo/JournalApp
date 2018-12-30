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

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osaigbovo.journalapp.models.Home;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class HomeListViewModel extends ViewModel {
    private static String TAG = "HomeListViewModel";

    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference dataRef =
            FirebaseDatabase.getInstance().getReference()
                    .child("users").child(userId).child("journals");

    HomeQueryLiveData mLiveData = new HomeQueryLiveData(dataRef.orderByChild("dates"));
    LiveData<List<Home>> mHomeLiveData;
    private String clubkey;
    private String journalKeys;

    private List<Home> mList = new ArrayList<>();

    private MutableLiveData<String> editLiveData = new MutableLiveData<>();
    private LiveData<String> resultKey;
    private MutableLiveData<String> jo = new MutableLiveData<>();

    @Inject
    public HomeListViewModel() {
        mHomeLiveData = Transformations.map(mLiveData, new Deserializer());

        /*resultKey = Transformations.switchMap(editLiveData, home -> {
            dataRef.orderByChild("entry")
                    .equalTo(home)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                String journalKey = childSnapshot.getKey();
                                journalKeys = journalKey;
                                jo.setValue(journalKey);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
            return jo;
        });*/

    }

    @NonNull
    public LiveData<List<Home>> getHomeListLiveData() {
        return mHomeLiveData;
    }

    private class Deserializer implements Function<DataSnapshot, List<Home>> {
        @Override
        public List<Home> apply(DataSnapshot dataSnapshot) {
            mList.clear();
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                Home h = snap.getValue(Home.class);
                mList.add(h);
            }
            return mList;
        }
    }

    public LiveData<String> getResults() {
        return resultKey;
    }

    public void setHome(Home home) {
        this.editLiveData.setValue(home.getEntry());
    }

    public String getJournal(Home home) {
        dataRef.orderByChild("entry")
                .equalTo(home.getEntry())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String journalKey = childSnapshot.getKey();
                            journalKeys = journalKey;
                            Log.e(TAG, journalKeys);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return journalKeys;
    }

    public void deleteJournal(Home home) {
        dataRef.orderByChild("entry")
                .equalTo(home.getEntry())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            clubkey = childSnapshot.getKey();
                            dataRef.child(clubkey).removeValue();
                            dataRef.getParent().child("dates").child(clubkey).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}