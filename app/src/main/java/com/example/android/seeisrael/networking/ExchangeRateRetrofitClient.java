package com.example.android.seeisrael.networking;

import com.example.android.seeisrael.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExchangeRateRetrofitClient {

    private static Retrofit mRetrofit = null;


    public static Retrofit getExchangeRatesRetrofitInstance() {

        // create a retrofit instance, only if one hasn't been created yet
        if (mRetrofit == null) {

            //Create OkHttpClient.Builder object
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            okHttpClientBuilder.connectTimeout(Constants.CONNECTION_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS);


            // create new Retrofit Object and attach to it the OkHttp client
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.EXCHANGE_RATES_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }

        return mRetrofit;
    }
}
