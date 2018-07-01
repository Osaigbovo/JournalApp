package com.osaigbovo.journalapp.di;

import com.osaigbovo.journalapp.ui.calender.CalenderFragment;
import com.osaigbovo.journalapp.ui.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//@Suppress("unused")
@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract CalenderFragment contributeCalenderFragment();

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();
}
