package com.news.diagnaltestapp.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.news.diagnaltestapp.data.MoviesRepository;
import com.news.diagnaltestapp.data.model.Content;
import com.news.diagnaltestapp.data.model.PageContent;
import com.news.diagnaltestapp.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by sreehari
 * on 15/8/19.
 */
class MoviesListViewModel extends ViewModel {
    private MoviesRepository moviesRepository;
    private LiveData<PageContent> moviesData;
    private MutableLiveData<List<Content>> searchedMoviesData;
    private List<Content> totalMoviesList = new ArrayList<>();

    MoviesListViewModel(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        searchedMoviesData = new MutableLiveData<>();
        fetchMoviesFromRepository();
    }

    public List<Content> getTotalMoviesList() {
        return totalMoviesList;
    }

    private void fetchMoviesFromRepository() {
        moviesData = moviesRepository.getMoviesData(Constants.NUMBER_ONE);
    }

    void fetchMoviesFromRepositoryNextPage(int pageNumber) {
        moviesData = moviesRepository.getMoviesData(pageNumber);
    }

    void updateTotalMoviesList(List<Content> content) {
        totalMoviesList.addAll(content);
    }

    LiveData<PageContent> getMoviesData() {
        return moviesData;
    }

    LiveData<List<Content>> getSearchedMoviesData() {
        return searchedMoviesData;
    }

    void searchMoviesList(String s) {
        List<Content> resultList = new ArrayList<>();
        for(Content movie: totalMoviesList) {
            if(movie.getName().toLowerCase().contains(s.toLowerCase())) {
                resultList.add(movie);
            }
        }
        searchedMoviesData.postValue(resultList);
    }
}
