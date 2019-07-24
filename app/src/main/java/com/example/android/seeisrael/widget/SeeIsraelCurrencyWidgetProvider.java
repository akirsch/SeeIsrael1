package com.example.android.seeisrael.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.ExchangeRatesActivity;
import com.example.android.seeisrael.models.ExchangeRatesResponse;
import com.example.android.seeisrael.repositories.CurrencyDataRepository;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.utils.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class SeeIsraelCurrencyWidgetProvider extends AppWidgetProvider {

    private static final String TAG = SeeIsraelCurrencyWidgetProvider.class.getSimpleName();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String dollarRate, String poundRate,
                                String euroRate, String randRate, String roubleRate, String caDollarRate) {

        Log.d(TAG, "updateAppWidget method called in widget provider");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.see_israel_currency_widget);

        // Create intent to launch ExchangeRatesActivity when clicked
        Intent exchangeRatesIntent = new Intent(context, ExchangeRatesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, exchangeRatesIntent, 0);

        views.setOnClickPendingIntent(R.id.canadian_dollar_rate_view, pendingIntent);

        // update all the views with the strings passed in to the method provided by the IntentService
        views.setTextViewText(R.id.dollar_rate_view, dollarRate);
        views.setTextViewText(R.id.pound_rate_view, poundRate);
        views.setTextViewText(R.id.euro_rate_view, euroRate);
        views.setTextViewText(R.id.rand_rate_view, randRate);
        views.setTextViewText(R.id.rouble_rate_view, roubleRate);
        views.setTextViewText(R.id.canadian_dollar_rate_view, caDollarRate);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void updateExchangeRateWidgets (Context context, AppWidgetManager appWidgetManager,
                                                  int[] appWidgetIds, String dollarRate, String poundRate,
                                                  String euroRate, String randRate, String roubleRate, String caDollarRate){
        Log.d(TAG, "updateExchangeRateWidgets method called in widget provider");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, dollarRate,
                    poundRate, euroRate, randRate, roubleRate, caDollarRate);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Start the intent service action update widget, which takes care of updating the widget UI
        Log.d(TAG, "onUpdate method in widgetProvider called");
        SeeIsraelWidgetService.startActionUpdateWidget(context);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

