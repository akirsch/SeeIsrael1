package com.example.android.seeisrael.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.interfaces.ExchangeRatesApiService;
import com.example.android.seeisrael.models.ExchangeRatesResponse;
import com.example.android.seeisrael.networking.ExchangeRateRetrofitClient;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.utils.Constants;
import com.example.android.seeisrael.workmanager.CurrencyApiQueryWorker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRatesFragment extends Fragment {

    private Unbinder mUnbinder;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ExchangeRatesApiService exchangeRatesApiService;
    private ExchangeRatesResponse exchangeRatesResponse;
    private boolean hasApiCallBeenMade;
    private double USDollarRate;
    private double poundRate;
    private double randRate;
    private double rubleRate;
    private double euroRate;
    private double canadianDollarRate;

    @BindView(R.id.date_tv)
    TextView currentDateView;

    @BindView(R.id.usd_live_rate_view)
    TextView USDollarRateDisplayView;

    @BindView(R.id.gbp_live_rate_view)
    TextView poundsRateDisplayView;

    @BindView(R.id.ruble_live_rate_view)
    TextView rubleRateDisplayView;

    @BindView(R.id.euro_live_rate_view)
    TextView euroRateDisplayView;

    @BindView(R.id.rand_live_rate_view)
    TextView randRateDisplayView;

    @BindView(R.id.ca_dollar_live_rate_view)
    TextView canadianDollarRateDisplayView;

    public ExchangeRatesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_echange_rates, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        displayCurrentDateInView();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Context context = getContext();

        if (savedInstanceState == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            editor = sharedPreferences.edit();

            // only set the SharedPreference boolean value for if the currency api has been called to be false the first
            // time the app is run on the device, i.e. when SharedPreferences does not yet contain a
            // value for this boolean. After the call has been made once, the value will be set to true.

            if (!sharedPreferences.contains(getString(R.string.currency_api_has_been_called_key))) {

                editor.putBoolean(getString(R.string.currency_api_has_been_called_key), false);

                editor.apply();
            }

            // only if the boolean value if false, run the api request from the activity and populate the
            // text views in the Fragment with the results, otherwise, the api call will be made and the
            // exchange rates updated from a background task.
            hasApiCallBeenMade = sharedPreferences
                    .getBoolean(getString(R.string.currency_api_has_been_called_key), false);


            if (!hasApiCallBeenMade) {

                exchangeRatesApiService = ExchangeRateRetrofitClient
                        .getExchangeRatesRetrofitInstance().create(ExchangeRatesApiService.class);

                exchangeRatesApiService
                        .getExchangeRates(Constants.BASE_CURRENCY, Constants.CONVERSION_CURRENCIES)
                        .enqueue(new Callback<ExchangeRatesResponse>() {
                            @Override
                            public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {

                                // if call is successful, get the current exchange rates and display them in UI
                                if (response.isSuccessful() && response.body() != null) {

                                    exchangeRatesResponse = response.body();

                                    updateUIAndPersistData(exchangeRatesResponse);

                                    // show Toast Message to inform the user of the date
                                    // of accuracy of the rates being shown
                                    Toast.makeText(getContext(),
                                            getString(R.string.date_of_exchange_rates) + exchangeRatesResponse.date,
                                            Toast.LENGTH_LONG).show();

                                    // if api call returns a successful result, record this in SharedPreferences
                                    editor.putBoolean(getString(R.string.currency_api_has_been_called_key), true);
                                    editor.apply();

                                }
                            }

                            @Override
                            public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {

                                Toast.makeText(getContext(), getString(R.string.exchange_rate_api_error_message),
                                        Toast.LENGTH_SHORT).show();


                            }
                        });

                // set up background WorkManager Worker task to call API once a day and update the shared preferences.
                setUpPeriodicWorkRequest(context);



                // if api has been successfully called once before, update UI from SharedPreference values,
                // which will be updated using the WorkManager Api
            } else {

                String defaultString = getString(R.string.default_value_string);

                // update dollar rate
                USDollarRateDisplayView
                        .setText(sharedPreferences.getString(getString(R.string.us_dollar_rate_key), defaultString));

                // update pound rate
                poundsRateDisplayView
                        .setText(sharedPreferences.getString(getString(R.string.gbp_rate_key), defaultString));

                // update euro rate
                euroRateDisplayView
                        .setText(sharedPreferences.getString(getString(R.string.euro_rate_key), defaultString));

                // update ruble rate
                rubleRateDisplayView
                        .setText(sharedPreferences.getString(getString(R.string.ruble_rate_key), defaultString));

                // update rand rate
                randRateDisplayView.setText(sharedPreferences.getString(getString(R.string.rand_rate_key), defaultString));

                // update canadian dollar rate
                canadianDollarRateDisplayView
                        .setText(sharedPreferences.getString(getString(R.string.canadian_dollar_rate_key), defaultString));

            }

            // if activity re-created after configuration change, populate the text views with the persisted data
        } else {
            USDollarRateDisplayView.setText(savedInstanceState.getCharSequence(Constants.CURRENT_USD_RATE_KEY));
            poundsRateDisplayView.setText(savedInstanceState.getCharSequence(Constants.CURRENT_GBP_RATE_KEY));
            euroRateDisplayView.setText(savedInstanceState.getCharSequence(Constants.CURRENT_EUR_RATE_KEY));
            randRateDisplayView.setText(savedInstanceState.getCharSequence(Constants.CURRENT_ZAR_RATE_KEY));
            rubleRateDisplayView.setText(savedInstanceState.getCharSequence(Constants.CURRENT_RUB_RATE_KEY));
            canadianDollarRateDisplayView.setText(savedInstanceState.getCharSequence(Constants.CURRENT_CAD_RATE_KEY));
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save current contents of text view displaying the exchange rates
        // to persist them upon configuration change
        outState.putString(Constants.CURRENT_USD_RATE_KEY, USDollarRateDisplayView.getText().toString());
        outState.putString(Constants.CURRENT_GBP_RATE_KEY, poundsRateDisplayView.getText().toString());
        outState.putString(Constants.CURRENT_EUR_RATE_KEY, euroRateDisplayView.getText().toString());
        outState.putString(Constants.CURRENT_RUB_RATE_KEY, rubleRateDisplayView.getText().toString());
        outState.putString(Constants.CURRENT_ZAR_RATE_KEY, randRateDisplayView.getText().toString());
        outState.putString(Constants.CURRENT_CAD_RATE_KEY, canadianDollarRateDisplayView.getText().toString());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * This method gets the current date and displays it in the date text view.
     */
    private void displayCurrentDateInView() {

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
        date = dateFormat.format(calendar.getTime());

        currentDateView.setText(date);

    }

    private void updateUIAndPersistData(ExchangeRatesResponse exchangeRatesResponse) {

        // display and save dollar rate
        USDollarRate = exchangeRatesResponse.rates.USDollars;
        String dollarRateAsString = Config.getForeignCurrencyToShekelRateAsString(USDollarRate);
        // get foreign currency to shekel rate, and convert it to a rounded string to display in UI
        USDollarRateDisplayView.setText(dollarRateAsString);

        // repeat this for all exchange rates

        //GBP Rate
        poundRate = exchangeRatesResponse.rates.britishPounds;
        String poundRateAsString = Config.getForeignCurrencyToShekelRateAsString(poundRate);
        poundsRateDisplayView.setText(poundRateAsString);

        // Euro Rate
        euroRate = exchangeRatesResponse.rates.euro;
        String euroRateAsString = Config.getForeignCurrencyToShekelRateAsString(euroRate);
        euroRateDisplayView.setText(euroRateAsString);

        // Rand Rate
        randRate = exchangeRatesResponse.rates.southAfricanRand;
        String randRateAsString = Config.getForeignCurrencyToShekelRateAsString(randRate);
        randRateDisplayView.setText(randRateAsString);

        // Ruble Rate
        rubleRate = exchangeRatesResponse.rates.roubles;
        String rubleRateAsString = Config.getForeignCurrencyToShekelRateAsString(rubleRate);
        rubleRateDisplayView.setText(randRateAsString);

        // Canadian Dollar Rate
        canadianDollarRate = exchangeRatesResponse.rates.canadianDollars;
        String canadianDollarRateAsString = Config.getForeignCurrencyToShekelRateAsString(canadianDollarRate);
        canadianDollarRateDisplayView.setText(canadianDollarRateAsString);


        // persist values in SharedPreferences
        editor.putString(getString(R.string.us_dollar_rate_key), dollarRateAsString)
                .putString(getString(R.string.gbp_rate_key), poundRateAsString)
                .putString(getString(R.string.euro_rate_key), euroRateAsString)
                .putString(getString(R.string.euro_rate_key), euroRateAsString)
                .putString(getString(R.string.ruble_rate_key), rubleRateAsString)
                .putString(getString(R.string.canadian_dollar_rate_key), canadianDollarRateAsString)
                .apply();
    }

    /**
     * helper method to query the currency api once a day to get the updated rates for that day.
     */
    private void setUpPeriodicWorkRequest(Context context){

        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(CurrencyApiQueryWorker.class, 24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // ensure that work manager instance isn't created every time the fragment is created
        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(getString(R.string.unique_work_request_id),
                        ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);


    }
}


