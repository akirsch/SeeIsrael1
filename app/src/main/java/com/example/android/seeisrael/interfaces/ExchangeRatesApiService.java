package com.example.android.seeisrael.interfaces;

import com.example.android.seeisrael.models.ExchangeRatesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeRatesApiService {

    @GET("latest")
    Call<ExchangeRatesResponse> getExchangeRates(@Query("base") String base,
                                                 @Query("symbols") String currencies);
}
