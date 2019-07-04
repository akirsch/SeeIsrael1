package com.example.android.seeisrael.database;

import android.content.Context;
import android.util.Log;

import com.example.android.seeisrael.models.Place;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Place.class}, version = 2, exportSchema = false)

@TypeConverters(Converters.class)

public abstract class PlacesDatabase extends RoomDatabase {

    private static final String LOG_TAG = PlacesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favorite_places";
    private static PlacesDatabase sInstance;

    public static PlacesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PlacesDatabase.class, PlacesDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavoritePlacesDao favoritePlacesDao();


}
