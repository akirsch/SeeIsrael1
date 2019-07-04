package com.example.android.seeisrael.viewmodels;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.seeisrael.database.PlacesDatabase;
import com.example.android.seeisrael.models.Place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddPlaceViewModel extends ViewModel {

    private final String TAG = AddPlaceViewModel.class.getSimpleName();
    private LiveData<Place> place;

    public AddPlaceViewModel(PlacesDatabase database, String placeId) {
        place = database.favoritePlacesDao().loadPlaceById(placeId);
    }

    public LiveData<Place> getPlace() {
        return place;
    }

    public void deletePlace(PlacesDatabase database, Place place) {

        // create array of objects for passing to AsyncTask
        Object[] asyncTaskObjectArray = new Object[2];
        asyncTaskObjectArray[0] = database;
        asyncTaskObjectArray[1] = place;

        new DeleteAsyncTask().execute(asyncTaskObjectArray);
    }


    public void insertPlace(PlacesDatabase database, Place place) {

        // create array of objects for passing to AsyncTask
        Object[] asyncTaskObjectArray = new Object[2];
        asyncTaskObjectArray[0] = database;
        asyncTaskObjectArray[1] = place;

        // manage insertion or deletion of data on a background thread using AsyncTask
        new InsertAsyncTask().execute(asyncTaskObjectArray);

    }

    private class InsertAsyncTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... objects) {

            PlacesDatabase database = (PlacesDatabase) objects[0];
            Place place = (Place) objects[1];

            Log.d(TAG, "inserting place in database");
            database.favoritePlacesDao().insertPlace(place);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "entry inserted into database");

        }
    }


    private class DeleteAsyncTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... objects) {

            PlacesDatabase database = (PlacesDatabase) objects[0];
            Place place = (Place) objects[1];


            Log.d(TAG, "deleting place from database");
            database.favoritePlacesDao().deletePlace(place);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "entry deleted from database");
        }
    }
}



