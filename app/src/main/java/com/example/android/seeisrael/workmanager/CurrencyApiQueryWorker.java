package com.example.android.seeisrael.workmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.interfaces.ExchangeRatesApiService;
import com.example.android.seeisrael.models.ExchangeRatesResponse;
import com.example.android.seeisrael.networking.ExchangeRateRetrofitClient;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.utils.Constants;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyApiQueryWorker extends Worker {

    private Context mContext;
    private boolean wasApiCallSuccessfull;

    ExchangeRatesApiService exchangeRatesApiService;

    ExchangeRatesResponse exchangeRatesResponse;

    public CurrencyApiQueryWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {


        exchangeRatesApiService = ExchangeRateRetrofitClient
                .getExchangeRatesRetrofitInstance()
                .create(ExchangeRatesApiService.class);



        exchangeRatesApiService.getExchangeRates(Constants.BASE_CURRENCY,
                Constants.CONVERSION_CURRENCIES).enqueue(new Callback<ExchangeRatesResponse>() {


            @Override
            public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {


                if (response.isSuccessful() && response.body() != null){
                    exchangeRatesResponse = response.body();

                    String dollarRateAsString =
                            Config.getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.USDollars);

                    String poundRateAsString = Config
                            .getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.britishPounds);

                    String euroRateAsString = Config
                            .getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.euro);

                    String randRateAsString = Config
                            .getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.southAfricanRand);

                    String rubleRateAsString = Config
                            .getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.roubles);

                    String caDollarsAsString = Config
                            .getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.canadianDollars);


                    // store the exchange rates in shared preferences so that they can be accessed
                    // by the exchange rates fragment and the widget
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(mContext);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(mContext.getString(R.string.us_dollar_rate_key), dollarRateAsString)
                            .putString(mContext.getString(R.string.gbp_rate_key), poundRateAsString)
                            .putString(mContext.getString(R.string.euro_rate_key), euroRateAsString)
                            .putString(mContext.getString(R.string.ruble_rate_key), rubleRateAsString)
                            .putString(mContext.getString(R.string.rand_rate_key), randRateAsString)
                            .putString(mContext.getString(R.string.canadian_dollar_rate_key), caDollarsAsString)
                            .apply();

                    wasApiCallSuccessfull = true;

                }


            }




            @Override
            public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {

                wasApiCallSuccessfull = false;
            }
        });

        if (wasApiCallSuccessfull){
            return Result.success();

        } else return Result.failure();


    }
}
