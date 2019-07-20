package com.example.android.seeisrael.repositories;

import com.example.android.seeisrael.interfaces.SygicPlacesApiService;
import com.example.android.seeisrael.models.PlaceDetailsMainBodyResponse;
import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.networking.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelDataRepository {

    private static TravelDataRepository travelDataRepository;

    private SygicPlacesApiService sygicPlacesApiService;

    public TravelDataRepository(){

        sygicPlacesApiService = RetrofitClientInstance
                .getRetrofitInstance()
                .create(SygicPlacesApiService.class);
    }

    public static TravelDataRepository getInstance(){

        if (travelDataRepository == null){

            travelDataRepository = new TravelDataRepository();
        }

        return travelDataRepository;
    }

    /**
     * This method manages the api call to the Sygic API to get the data for the list of
     * Towns in Israel to be displayed in the Main Activity
     *
     * @return A mutable live data object containing this data
     */
    public MutableLiveData<TownQueryMainBodyResponse> getAllTowns(){

        MutableLiveData<TownQueryMainBodyResponse> allTownsDataResponseMutableLiveData =
                new MutableLiveData<>();

        sygicPlacesApiService.getAllTownsInIsrael().enqueue(new Callback<TownQueryMainBodyResponse>() {
            @Override
            public void onResponse(Call<TownQueryMainBodyResponse> call, Response<TownQueryMainBodyResponse> response) {

                if (response.isSuccessful() && response.body() != null){

                    allTownsDataResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TownQueryMainBodyResponse> call, Throwable t) {

                allTownsDataResponseMutableLiveData.setValue(null);

            }
        });

        return allTownsDataResponseMutableLiveData;
    }

    /**
     * This method manages the api call to the Sygic API to get the data for the list of
     * locations for a specific category to be displayed in the LocationsCategoriesListsFragment
     *
     * @return A mutable live data object containing this data
     */
    public MutableLiveData<TownQueryMainBodyResponse> getCategorySpecificLocationsList(String id, String category, int limit){

        MutableLiveData<TownQueryMainBodyResponse> locationsByCategoryDataResponseMutableLiveData =
                new MutableLiveData<>();

        sygicPlacesApiService.getListOfPlacesByCategory(id, category, limit).enqueue(new Callback<TownQueryMainBodyResponse>() {
            @Override
            public void onResponse(Call<TownQueryMainBodyResponse> call, Response<TownQueryMainBodyResponse> response) {

                if (response.isSuccessful() && response.body() != null){

                    locationsByCategoryDataResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TownQueryMainBodyResponse> call, Throwable t) {

                locationsByCategoryDataResponseMutableLiveData.setValue(null);
            }
        });

        return locationsByCategoryDataResponseMutableLiveData;
    }

    /**
     * This method manages the api call to the Sygic API to get the data for the details of the chosen
     * location to be displayed in the LocationsDetailsFragment
     *
     * @return A mutable live data object containing this data
     */
    public MutableLiveData<PlaceDetailsMainBodyResponse> getPlaceDetails(String id){

        MutableLiveData<PlaceDetailsMainBodyResponse> placeDetailsMainBodyResponseMutableLiveData =
                new MutableLiveData<>();

        sygicPlacesApiService.getPlaceDetails(id).enqueue(new Callback<PlaceDetailsMainBodyResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsMainBodyResponse> call, Response<PlaceDetailsMainBodyResponse> response) {

                if (response.isSuccessful() && response.body() != null){
                    placeDetailsMainBodyResponseMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<PlaceDetailsMainBodyResponse> call, Throwable t) {

                placeDetailsMainBodyResponseMutableLiveData.setValue(null);
            }
        });

        return placeDetailsMainBodyResponseMutableLiveData;
    }


}
