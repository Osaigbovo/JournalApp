package com.osaigbovo.journalapp.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.osaigbovo.journalapp.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final static String TAG = "Adapter";
    private List<Home> mhomeList;


    void setHomeList(final List<Home> homeList) {
        mhomeList = homeList;
        notifyDataSetChanged();
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewHolder homeViewHolder, int position) {
        Home home = mhomeList.get(position);
        homeViewHolder.bind(home);
    }

    @Override
    public int getItemCount() {
        return mhomeList == null ? 0 : mhomeList.size();
    }
}
