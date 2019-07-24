package com.example.android.seeisrael.repositories;

import com.example.android.seeisrael.interfaces.ExchangeRatesApiService;
import com.example.android.seeisrael.models.ExchangeRatesResponse;
import com.example.android.seeisrael.models.Rates;
import com.example.android.seeisrael.networking.ExchangeRateRetrofitClient;
import com.example.android.seeisrael.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyDataRepository {

    private static CurrencyDataRepository currencyDataRepository;

    private ExchangeRatesApiService exchangeRatesApiService;

    private ExchangeRatesResponse exchangeRatesResponse;

    private Rates latestRates;

    public CurrencyDataRepository(){

        exchangeRatesApiService = ExchangeRateRetrofitClient
                .getExchangeRatesRetrofitInstance()
                .create(ExchangeRatesApiService.class);
    }

    public static CurrencyDataRepository getInstance(){

        if (currencyDataRepository == null) {

            currencyDataRepository = new CurrencyDataRepository();
        }

        return currencyDataRepository;
    }

    public ExchangeRatesResponse getExchangeRateData(){

        exchangeRatesApiService
                .getExchangeRates(Constants.BASE_CURRENCY,
                        Constants.CONVERSION_CURRENCIES).enqueue(new Callback<ExchangeRatesResponse>() {
            @Override
            public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                if (response.isSuccessful() && response.body() != null ){
                    exchangeRatesResponse = response.body();

                }
            }

            @Override
            public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {

                exchangeRatesResponse = null;
            }
        });

        return exchangeRatesResponse;
    }
}
