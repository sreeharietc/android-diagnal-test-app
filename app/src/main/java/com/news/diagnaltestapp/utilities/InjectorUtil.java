package com.news.diagnaltestapp.utilities;

import android.content.Context;

import com.news.diagnaltestapp.data.MoviesDataSource;
import com.news.diagnaltestapp.data.MoviesRepository;
import com.news.diagnaltestapp.ui.list.MoviesListViewModelFactory;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class InjectorUtil {
    public static MoviesListViewModelFactory provideMoviesListViewModelFactory(Context context) {
        MoviesRepository moviesRepository = provideMoviesRepository(context);
        return new MoviesListViewModelFactory(moviesRepository);
    }

    private static MoviesRepository provideMoviesRepository(Context context) {
        MoviesDataSource moviesDataSource = provideMoviesDataSource(context);
        return MoviesRepository.getInstance(moviesDataSource);
    }

    private static MoviesDataSource provideMoviesDataSource(Context context) {
        return MoviesDataSource.getInstance(context.getAssets());
    }
}
