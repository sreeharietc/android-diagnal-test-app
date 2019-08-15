package com.news.diagnaltestapp.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.news.diagnaltestapp.data.MoviesRepository;
import com.news.diagnaltestapp.data.model.PageContent;

/**
 * Created by sreehari
 * on 15/8/19.
 */
class MoviesListViewModel extends ViewModel {
    private MoviesRepository moviesRepository;
    private LiveData<PageContent> moviesData;

    MoviesListViewModel(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        fetchMoviesFromRepository();
    }

    private void fetchMoviesFromRepository() {
        moviesData = moviesRepository.getMoviesData();
    }

    LiveData<PageContent> getMoviesData() {
        return moviesData;
    }
}
