package com.news.diagnaltestapp.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.news.diagnaltestapp.data.MoviesRepository;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class MoviesListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    MoviesRepository moviesRepository;

    public MoviesListViewModelFactory(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MoviesListViewModel(moviesRepository);
    }
}
