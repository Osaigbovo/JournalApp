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

    /*
    @Singleton
    @Provides
    RequestInterface provideMoviesService() {
        return ServiceGenerator.createService(RequestInterface.class);
    }

    @Singleton
    @Provides
    CinemaDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, CinemaDatabase.class, "KadaCinemas.db")
                //.addMigrations(CinemaDatabase.MIGRATION_1_2)
                //.fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    TopMoviesDao provideTopMoviesDao(CinemaDatabase db) {
        return db.topMoviesDao();
    }

    @Singleton
    @Provides
    MovieDetailDao provideMovieDetailDao(CinemaDatabase db) {
        return db.movieDetailDao();
    }

    @Singleton
    @Provides
    FavDao provideFavMoviesDao(CinemaDatabase db) {
        return db.favDao();
    }

    @Singleton
    @Provides
    NowPlayingDao provideNowPlayingDao(CinemaDatabase db) {
        return db.nowPlayingDao();
    }

    @Singleton
    @Provides
    UpcomingDao provideComingDao(CinemaDatabase db) {  return db.upcomingDao();}*/

}
