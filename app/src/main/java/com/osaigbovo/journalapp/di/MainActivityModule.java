package com.osaigbovo.journalapp.di;

import com.osaigbovo.journalapp.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//@Suppress("unused")
@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}
