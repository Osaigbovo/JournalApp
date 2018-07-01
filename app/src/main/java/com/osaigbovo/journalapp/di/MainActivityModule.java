package com.osaigbovo.journalapp.di;

import com.osaigbovo.journalapp.MainActivity;
import com.osaigbovo.journalapp.ui.journal.JournalActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//@Suppress("unused")
@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract JournalActivity contributeJournalActivity();
}
