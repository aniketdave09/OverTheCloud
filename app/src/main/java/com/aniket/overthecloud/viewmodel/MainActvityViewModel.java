package com.aniket.overthecloud.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aniket.overthecloud.data.model.MainResponse;
import com.aniket.overthecloud.data.model.Row;
import com.aniket.overthecloud.data.repository.MovieRepository;

import java.util.List;

public class MainActvityViewModel extends ViewModel {
    private MutableLiveData<List<Row>> movieRows = new MutableLiveData<>();
    private MovieRepository repository;

    public MainActvityViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Row>> getMovieRows() {
        return movieRows;
    }

    public void loadMovies() {
        MainResponse response = repository.getMoviesData();
        if (response != null) {
            movieRows.setValue(response.getRows());
        }
    }
}