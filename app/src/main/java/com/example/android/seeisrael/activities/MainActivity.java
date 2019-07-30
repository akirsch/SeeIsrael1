package com.example.android.seeisrael.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.models.Places;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Places> sPlacesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set App Bar title to name of project
        Toolbar myToolbar = findViewById(R.id.toolbar);
        // Set the Toolbar as Action Bar
        setSupportActionBar(myToolbar);
        // Set title of action bar to appropriate label for this Activity
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        myToolbar.setPadding(0, 25, 0, 0);


        MobileAds.initialize(this, getString(R.string.adMob_app_id));
        Log.d(MainActivity.class.getSimpleName(), "MobileAds initialized");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_go_to_exchange_rates:

                Intent exchangeRatesActivityIntent = new Intent(this, ExchangeRatesActivity.class);

                if (exchangeRatesActivityIntent.resolveActivity(this.getPackageManager()) != null) {
                    this.startActivity(exchangeRatesActivityIntent);
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}