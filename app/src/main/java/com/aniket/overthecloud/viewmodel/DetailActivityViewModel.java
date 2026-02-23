package com.aniket.overthecloud.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.aniket.overthecloud.data.repository.MovieRepository;

public class DetailActivityViewModel implements ViewModelProvider.Factory {
    private final MovieRepository repository;

    public DetailActivityViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActvityViewModel.class)) {
            return (T) new MainActvityViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}