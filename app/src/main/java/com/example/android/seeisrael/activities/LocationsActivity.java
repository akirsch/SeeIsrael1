package com.example.android.seeisrael.activities;

import android.app.Activity;
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


        // get reference to embedded Fragment
        categoriesListsFragment = (LocationsCategoriesListsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_category_selection_fragment);


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
