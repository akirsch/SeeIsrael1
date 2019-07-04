package com.example.android.seeisrael.viewmodels;

import com.example.android.seeisrael.database.PlacesDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddPlaceViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PlacesDatabase placesDatabase;
    private final String placeId;

    public AddPlaceViewModelFactory (PlacesDatabase database, String id){

        placesDatabase = database;
        placeId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddPlaceViewModel(placesDatabase, placeId);
    }
}
