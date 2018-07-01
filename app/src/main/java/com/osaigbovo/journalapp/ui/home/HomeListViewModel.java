package com.osaigbovo.journalapp.ui.home;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osaigbovo.journalapp.models.Home;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeListViewModel extends ViewModel {
    private static String TAG = "HomeListViewModel";

    private static final DatabaseReference dataRef =
            FirebaseDatabase.getInstance().getReference().child("journal").child("journals");

    HomeQueryLiveData mLiveData = new HomeQueryLiveData(dataRef);
    LiveData<List<Home>> mHomeLiveData;
    String clubkey;
    String key;

    private List<Home> mList = new ArrayList<>();

    @Inject
    public HomeListViewModel() {
        mHomeLiveData = Transformations.map(mLiveData, new Deserializer());
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

   /* public String getKeyHome(Home home) {

        dataRef.orderByChild("entry")
                .equalTo(home.getEntry())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            key = childSnapshot.getKey();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return key;
    }*/

}