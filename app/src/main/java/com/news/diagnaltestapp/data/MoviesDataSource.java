package com.news.diagnaltestapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.news.diagnaltestapp.data.model.PageContent;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class MoviesDataSource {
    private static MoviesDataSource sInstance;
    private MutableLiveData<PageContent> moviesData;
    private AssetManager assetManager;

    private MoviesDataSource(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.moviesData = new MutableLiveData<>();

    }

    public static MoviesDataSource getInstance(AssetManager assetManager) {
        if(sInstance == null) {
            sInstance = new MoviesDataSource(assetManager);
        }
        return sInstance;
    }

    LiveData<PageContent> getMoviesData() {
        getMoviesDataFromAssets();
        return moviesData;
    }

    private void getMoviesDataFromAssets() {
        String moviesDataJson = DataService.readFile(assetManager, "CONTENTLISTINGPAGE-PAGE1.json");
        PageContent pageContent = new Gson().fromJson(moviesDataJson, PageContent.class);
        moviesData.postValue(pageContent);
    }
}
