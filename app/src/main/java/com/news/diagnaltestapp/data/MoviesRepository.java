package com.news.diagnaltestapp.data;

import android.arch.lifecycle.LiveData;

import com.news.diagnaltestapp.data.model.PageContent;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class MoviesRepository {
    private static MoviesRepository sInstance;
    private MoviesDataSource moviesDataSource;

    private MoviesRepository(MoviesDataSource moviesDataSource) {
        this.moviesDataSource = moviesDataSource;
    }

    public static MoviesRepository getInstance(MoviesDataSource moviesDataSource) {
        if(sInstance == null) {
            sInstance = new MoviesRepository(moviesDataSource);
        }
        return sInstance;
    }

    public LiveData<PageContent> getMoviesData() {
        return moviesDataSource.getMoviesData();
    }
}
