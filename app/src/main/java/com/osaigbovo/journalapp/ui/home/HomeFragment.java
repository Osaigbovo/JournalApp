package com.osaigbovo.journalapp.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.models.Home;
import com.osaigbovo.journalapp.ui.journal.JournalActivity;

import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements HomeAdapter.OnItemSelectedListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private HomeAdapter mHomeAdapter = new HomeAdapter(this);

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeListViewModel homeListViewModel;

    @Inject
    Context context;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeListViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(false);
        //mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new Journal entry.
                Intent intent = new Intent(getContext(), JournalActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

        mRecyclerView.setAdapter(mHomeAdapter);
        // Update the list when the data changes
        if (homeListViewModel != null) {
            LiveData<List<Home>> liveData = homeListViewModel.getHomeListLiveData();
            liveData.observe(getActivity(), (List<Home> mHome) -> {
                mHomeAdapter.setHomeList(mHome);
            });
        }

        //DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), VERTICAL);
        //mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onSelected(Home home) {
        // Do something on select
    }

    @Override
    public void onMenuAction(Home home, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                // Add edit screen
                break;
            case R.id.menu_delete:
                //objects.remove(object);
                //setupObjectList();
                break;
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
    public void onDestroyView() {
        super.onDestroyView();
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
