package com.example.android.seeisrael.widget;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import com.example.android.seeisrael.R;
import com.example.android.seeisrael.interfaces.ExchangeRatesApiService;
import com.example.android.seeisrael.models.ExchangeRatesResponse;
import com.example.android.seeisrael.networking.ExchangeRateRetrofitClient;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.utils.Constants;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeIsraelWidgetService extends IntentService {

    private final String TAG = SeeIsraelWidgetService.class.getSimpleName();

    String dollarRate;
    String poundRate;
    String euroRate;
    String randRate;
    String roubleRate;
    String canDollarRate;


    public static final String ACTION_UPDATE_WIDGET = "com.example.android.seeisrael.widget.action.update_widget";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public SeeIsraelWidgetService() {
        super("SeeIsraelWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final String action  =  intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)){
                handleActionUpdateWidget();
            }
        }

    }

    /**
     * Starts this service to perform UpdateWidget action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, SeeIsraelWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }


    @SuppressLint("StaticFieldLeak")
    private void handleActionUpdateWidget(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(this, SeeIsraelCurrencyWidgetProvider.class));

        // If shared preferences already contain values for the current exchange rates,
        // this means the exchange rate api has already been called, so use these values to update the
        // widget UI.
        if (sharedPreferences.contains(getString(R.string.us_dollar_rate_key)) &&
                sharedPreferences.contains(getString(R.string.gbp_rate_key)) &&
                sharedPreferences.contains(getString(R.string.euro_rate_key)) &&
                sharedPreferences.contains(getString(R.string.rand_rate_key)) &&
                sharedPreferences.contains(getString(R.string.canadian_dollar_rate_key)) &&
                sharedPreferences.contains(getString(R.string.ruble_rate_key))) {

            dollarRate = sharedPreferences.getString(getString(R.string.us_dollar_rate_key),
                    getString(R.string.default_value_string));

            poundRate = sharedPreferences.getString(getString(R.string.gbp_rate_key),
                    getString(R.string.gbp_rate_key));

            euroRate = sharedPreferences.getString(getString(R.string.euro_rate_key),
                    getString(R.string.default_value_string));

            randRate = sharedPreferences.getString(getString(R.string.rand_rate_key),
                    getString(R.string.default_value_string));

            roubleRate = sharedPreferences.getString(getString(R.string.ruble_rate_key),
                    getString(R.string.default_value_string));

            canDollarRate = sharedPreferences.getString(getString(R.string.canadian_dollar_rate_key),
                    getString(R.string.default_value_string));


            SeeIsraelCurrencyWidgetProvider.updateExchangeRateWidgets(this, appWidgetManager,
                    appWidgetIds, dollarRate, poundRate, euroRate, randRate, roubleRate, canDollarRate);
            // if no values exist in SharedPreferences, call the API to get the data
        } else {

            ExchangeRatesApiService exchangeRatesApiService = ExchangeRateRetrofitClient
                    .getExchangeRatesRetrofitInstance()
                    .create(ExchangeRatesApiService.class);

            Log.d(TAG, "making call to api");

            exchangeRatesApiService.getExchangeRates(Constants.BASE_CURRENCY,
                    Constants.CONVERSION_CURRENCIES).enqueue(new Callback<ExchangeRatesResponse>() {
                @Override
                public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                    if (response.isSuccessful() && response.body() != null){

                        Log.d(TAG, "api call from service successful");

                        ExchangeRatesResponse exchangeRatesResponse = response.body();

                        dollarRate = Config.getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.USDollars);

                        poundRate = Config.getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.britishPounds);

                        euroRate = Config.getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.euro);

                        randRate = Config.getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.southAfricanRand);

                        roubleRate = Config.getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.roubles);

                        canDollarRate = Config.getForeignCurrencyToShekelRateAsString(exchangeRatesResponse.rates.canadianDollars);



                    }
                }

                @Override
                public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {

                    Log.d(TAG, "api call failed");
                }
            });

            SeeIsraelCurrencyWidgetProvider.updateExchangeRateWidgets(this, appWidgetManager,
                    appWidgetIds, dollarRate, poundRate, euroRate, randRate, roubleRate, canDollarRate);


        }

    }
}
