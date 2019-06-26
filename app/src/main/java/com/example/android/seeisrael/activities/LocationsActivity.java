package com.example.android.seeisrael.activities;

import android.os.Bundle;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.fragments.LocationsCategoriesListsFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LocationsActivity extends AppCompatActivity {

    private LocationsCategoriesListsFragment categoriesListsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        // Get the town selected from the intent which started this activity, and
        // pass it to the Fragment within this activity which displays
        // the viewpager with list of categories of locations to choose from


    }

    @Override
    public void onBackPressed() {
        if (categoriesListsFragment.getViewPagerCurrentPage() == 0){
            super.onBackPressed();

        } else {
            categoriesListsFragment.moveBackOnePage();
        }

    }
}
