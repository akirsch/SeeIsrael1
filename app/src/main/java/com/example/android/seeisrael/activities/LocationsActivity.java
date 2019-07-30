package com.example.android.seeisrael.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.fragments.LocationsCategoriesListsFragment;
import com.example.android.seeisrael.fragments.SplashFragment;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.utils.Constants;
import com.google.android.gms.ads.MobileAds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class LocationsActivity extends AppCompatActivity {

    private LocationsCategoriesListsFragment categoriesListsFragment;
    private Places currentPlace;
    private boolean isTwoPaneLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        // get reference to embedded Fragment
        categoriesListsFragment = (LocationsCategoriesListsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_category_selection_fragment);

        // handle UI behaviour is app is displaying in 2 pane mode on a tablet
        if (findViewById(R.id.tablet_landscape_linear_layout) != null) {

            // set boolean indicating two pane layout to true
            isTwoPaneLayout = true;

            // initialize the UI in both fragment containers
            if (savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                // as no location from the view pager has been selected yet, display a splash image
                // in the container which will display the location details once location is chosen

                SplashFragment splashFragment = new SplashFragment();
                // add fragment to container using transaction
                fragmentManager.beginTransaction()
                        .add(R.id.location_detail_fragment_container, splashFragment)
                        .commit();

                LocationsCategoriesListsFragment locationsCategoriesListsFragment
                        = new LocationsCategoriesListsFragment();

                // get chosen town from intent that started this Activity
                if (getIntent() != null) {
                    currentPlace = getIntent().getParcelableExtra(Constants.SELECTED_PLACES_KEY);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.SELECTED_PLACES_KEY, currentPlace);
                    bundle.putBoolean(Constants.IS_TWO_PANE_BOOLEAN_KEY, isTwoPaneLayout);
                    locationsCategoriesListsFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .add(R.id.location_category_selection_fragment_container,
                                    locationsCategoriesListsFragment)
                            .commit();

                }


            }


        }


    }

    @Override
    public void onBackPressed() {
        if (categoriesListsFragment.getViewPagerCurrentPage() == 0) {
            super.onBackPressed();

        } else {
            categoriesListsFragment.moveBackOnePage();
        }

    }
}
