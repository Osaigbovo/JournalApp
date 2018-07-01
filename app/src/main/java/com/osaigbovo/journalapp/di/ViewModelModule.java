package com.osaigbovo.journalapp.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.osaigbovo.journalapp.ui.calender.CalenderViewModel;
import com.osaigbovo.journalapp.ui.home.HomeListViewModel;
import com.osaigbovo.journalapp.ui.journal.JournalViewModel;
import com.osaigbovo.journalapp.viewmodel.JournalViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

//@Suppress("unused")
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CalenderViewModel.class)
    abstract ViewModel bindsCalenderViewModel(CalenderViewModel calenderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(JournalViewModel.class)
    abstract ViewModel bindsJournalViewModel(JournalViewModel journalViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeListViewModel.class)
    abstract ViewModel bindsWatchListViewModel(HomeListViewModel homeListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(JournalViewModelFactory factory);
}
