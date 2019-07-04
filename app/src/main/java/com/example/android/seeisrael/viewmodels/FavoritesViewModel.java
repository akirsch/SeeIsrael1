package com.example.android.seeisrael.viewmodels;

import android.app.Application;

import com.example.android.seeisrael.database.PlacesDatabase;
import com.example.android.seeisrael.models.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoritesViewModel extends AndroidViewModel {

    private final LiveData<List<Place>> favoritePlaces;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        PlacesDatabase placesDatabase = PlacesDatabase.getInstance(this.getApplication());
        favoritePlaces = placesDatabase.favoritePlacesDao().loadAllPlaces();

    }

    public LiveData<List<Place>> getFavoritePlaces() { return favoritePlaces;}
}
