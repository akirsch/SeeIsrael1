package com.example.android.seeisrael.interfaces;

import com.example.android.seeisrael.models.Town;
import com.example.android.seeisrael.models.TownListQueryResponseObject;
import com.example.android.seeisrael.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface SygicPlacesApiService {

    @Headers("x-api-key: " + Constants.SYGIC_PLACES_API_KEY_HEADER)
    @GET("list?parents=country:71&limit=20")
    Call<TownListQueryResponseObject> getAllTownsInIsrael();


}