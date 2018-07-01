package com.osaigbovo.journalapp.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application-wide dependencies.
 */
@Module(includes = ViewModelModule.class)
// Module means the class which contains methods who will provide dependencies.
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }
}
