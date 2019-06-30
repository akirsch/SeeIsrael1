package com.example.android.seeisrael.interfaces;

import com.example.android.seeisrael.models.PlaceDetailsMainBodyResponse;
import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SygicPlacesApiService {

    @Headers("x-api-key: " + Constants.SYGIC_PLACES_API_KEY_HEADER)
    @GET("list?parents=country:71&limit=20")
    Call<TownQueryMainBodyResponse> getAllTownsInIsrael();


    @Headers("x-api-key: " + Constants.SYGIC_PLACES_API_KEY_HEADER)
    @GET("list")
    Call<TownQueryMainBodyResponse> getListOfPlacesByCategory(@Query("parents") String id,
                                                              @Query("categories") String selectedCategory,
                                                              @Query("limit") int limit);


    @Headers("x-api-key: " + Constants.SYGIC_PLACES_API_KEY_HEADER)
    @GET("{id}")
    Call<PlaceDetailsMainBodyResponse> getPlaceDetails(@Path(value = "id") String id);
}