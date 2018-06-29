package com.osaigbovo.journalapp.ui.journal;

import android.arch.lifecycle.ViewModel;

public class JournalViewModel extends ViewModel {

   /* private final LiveData<Resource<List<TopMoviesEntity>>> popularMovies;
    private final LiveData<Resource<List<NowPlayingEntity>>> nowPlayingMovies;

    @Inject
    public JournalViewModel(TopMoviesRepository movieRepository, NowPlayingRepository nowPlayingRepository) {
        popularMovies = movieRepository.loadTopMovies(API_KEY);
        nowPlayingMovies = nowPlayingRepository.loadNowPlay(API_KEY);
    }

    public LiveData<Resource<List<NowPlayingEntity>>> getNowPlayMovies() {
        return nowPlayingMovies;
    }

    public LiveData<Resource<List<TopMoviesEntity>>> getPopularMovies() {
        return popularMovies;
    }*/

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
