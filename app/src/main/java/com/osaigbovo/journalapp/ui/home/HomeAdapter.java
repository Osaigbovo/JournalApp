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

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.models.Home;
import com.osaigbovo.journalapp.ui.journal.JournalActivity;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final static String TAG = "HomeAdapter";
    private List<Home> mhomeList;
    private HomeListViewModel homeListViewModel;
    private String journalKey;

    void setHomeList(HomeListViewModel homeListViewModel, final List<Home> homeList) {
        mhomeList = homeList;
        this.homeListViewModel = homeListViewModel;
        notifyDataSetChanged();
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewHolder homeViewHolder, int position) {
        final Home home = mhomeList.get(position);
        homeViewHolder.bind(home);

        homeViewHolder.mTextViewOption.setOnClickListener((View view) -> {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
            popup.setOnMenuItemClickListener((MenuItem item) -> {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        final Intent editIntent = new Intent(view.getContext(), JournalActivity.class);

                        journalKey = homeListViewModel.getJournal(home);
                        //Log.e(TAG, journalKey.toString());

                        /*// TODO 2 - What am I do?
                        homeListViewModel.setHome(home);
                        LiveData<String> keyLiveData = homeListViewModel.getResults();
                        keyLiveData.observe(, s -> {
                            editIntent.putExtra(JournalActivity.EXTRA_JOURNAL_KEY, s);
                        });*/

                        editIntent.putExtra(JournalActivity.EXTRA_JOURNAL_KEY, journalKey);
                        view.getContext().startActivity(editIntent);

                        break;
                    case R.id.menu_delete:
                        mhomeList.remove(home);
                        homeListViewModel.deleteJournal(home);
                        Toast.makeText(view.getContext(), "Journal for : " + home.getDate()
                                + " has been deleted.", Toast.LENGTH_LONG).show();

                        notifyItemRemoved(position);

                        break;
                }
                return false;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return mhomeList == null ? 0 : mhomeList.size();
    }
}
