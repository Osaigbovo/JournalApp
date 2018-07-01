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
