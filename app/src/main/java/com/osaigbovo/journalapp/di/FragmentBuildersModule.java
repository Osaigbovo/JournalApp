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
