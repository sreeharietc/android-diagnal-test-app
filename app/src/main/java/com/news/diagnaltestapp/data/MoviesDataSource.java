package com.news.diagnaltestapp.data;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class MoviesDataSource {
    private static MoviesDataSource sInstance;

    public static MoviesDataSource getInstance() {
        if(sInstance == null) {
            sInstance = new MoviesDataSource();
        }
        return sInstance;
    }
}
