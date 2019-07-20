package com.example.android.seeisrael.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LocationDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final String id;

    public LocationDetailsViewModelFactory (String id){

        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new LocationDetailsViewModel(id);

    }
}
