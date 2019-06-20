package com.example.android.seeisrael.interfaces;

import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface SygicPlacesApiService {

    @Headers("x-api-key: " + Constants.SYGIC_PLACES_API_KEY_HEADER)
    @GET("list?parents=country:71&limit=20")
    Call<TownQueryMainBodyResponse> getAllTownsInIsrael();

    @Headers("x-api-key: " + Constants.SYGIC_PLACES_API_KEY_HEADER)
    @GET("list?parents={id}&limit=80")
    Call<TownQueryMainBodyResponse> getAllPlacesInTown(@Path("id") String id);
}