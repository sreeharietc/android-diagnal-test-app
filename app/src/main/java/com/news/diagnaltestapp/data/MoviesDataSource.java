package com.news.diagnaltestapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.news.diagnaltestapp.data.model.PageContent;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class MoviesDataSource {
    private static MoviesDataSource sInstance;
    private MutableLiveData<PageContent> moviesData;

    public MoviesDataSource() {
        this.moviesData = new MutableLiveData<>();
    }

    public static MoviesDataSource getInstance() {
        if(sInstance == null) {
            sInstance = new MoviesDataSource();
        }
        return sInstance;
    }

    public LiveData<PageContent> getMoviesData() {
        getMoviesDataFromAssets();
        return moviesData;
    }

    private void getMoviesDataFromAssets() {

    }
}
