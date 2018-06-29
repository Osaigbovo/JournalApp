package com.osaigbovo.journalapp.di;

import android.arch.lifecycle.ViewModelProvider;

import com.osaigbovo.journalapp.viewmodel.JournalViewModelFactory;

import dagger.Binds;
import dagger.Module;

// @Suppress("unused")
@Module
public abstract class ViewModelModule {

    /*@Binds
    @IntoMap
    @ViewModelKey(JournalViewModel.class)
    abstract ViewModel bindsTheatersViewModel(JournalViewModel theatersViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel bindsMovieDetailViewModel(MovieDetailsViewModel movieDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(WatchListViewModel.class)
    abstract ViewModel bindsWatchListViewModel(WatchListViewModel watchListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ComingViewModel.class)
    abstract ViewModel bindsComingViewModel(ComingViewModel comingViewModel);*/

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(JournalViewModelFactory factory);
}
