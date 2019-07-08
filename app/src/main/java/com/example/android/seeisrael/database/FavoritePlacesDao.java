package com.example.android.seeisrael.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.android.seeisrael.models.Place;

import java.util.List;

@Dao
public interface FavoritePlacesDao {

    @Query("SELECT * FROM favorite_places")
    LiveData<List<Place>> loadAllPlaces();

    @Insert
    void insertPlace(Place place);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePlace(Place place);

    @Delete
    void deletePlace(Place place);

    @Query("SELECT * FROM favorite_places WHERE id = :id")
    Place loadPlaceById (String id);


}
