package com.example.android.seeisrael.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.ExchangeRatesActivity;
import com.example.android.seeisrael.activities.FavoritesActivity;
import com.example.android.seeisrael.adapters.LocationCategoryFragmentPagerAdapter;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.utils.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocationsCategoriesListsFragment extends Fragment {

    private Unbinder mUnbinder;
    private LocationCategoryFragmentPagerAdapter mLocationCategoryFragmentPagerAdapter;
    public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
    private Places chosenPlace;
    private boolean isBeingDisplayedInTwoPane;

    private final List<Pair<String, Fragment>> pages = new ArrayList<>();

    public LocationsCategoriesListsFragment() {
    }

    private Toolbar mToolbar;
    private AdView mAdView;

    @BindView(R.id.tab_layout)
    TabLayout mTablayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_location_category_selection, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        mTablayout.setupWithViewPager(mViewPager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // if this fragment was created with arguments directly from parent Activity,
        // it is being displayed in 2 pane mode on a tablet
        if (getArguments() != null){

            chosenPlace = getArguments().getParcelable(Constants.SELECTED_PLACES_KEY);
            isBeingDisplayedInTwoPane = getArguments().getBoolean(Constants.IS_TWO_PANE_BOOLEAN_KEY);

            // else this Fragment is being statically inflated by its parent Activity in single pane layout
        } else {
            // get chosen town from the parent Activity of this Fragment
            chosenPlace = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(Constants.SELECTED_PLACES_KEY);
            isBeingDisplayedInTwoPane = false;
        }


        mToolbar = getActivity().findViewById(R.id.toolbar);

        mAdView = Objects.requireNonNull(getActivity()).findViewById(R.id.adView);

        // load test add into AdView
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("3EB28E624CAD65EA74E3971763255324")
                .build();
        mAdView.loadAd(adRequest);

        // Set the Toolbar as Action Bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        // Set title of action bar to appropriate label for this Activity
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(chosenPlace.name);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        mToolbar.setPadding(0, 25, 0, 0);

        pages.add(new Pair<>(getString(R.string.discover),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.discovering_api_call_string), R.drawable.discover_palceholder,
                        isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.sightseeing),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.sightseeing_api_call_string), R.drawable.sightseeing_placeholder,
                        isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.eat),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.eating_api_call_string), R.drawable.eating_placeholder,
                        isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.shopping),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.shopping_api_call_string), R.drawable.shopping_placeholder,
                        isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.playing),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.playing_api_call_string), R.drawable.playing_placeholder,
                        isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.relaxing),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.relaxing_api_call_string), R.drawable.relaxing_placeholder,
                        isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.travelling),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.traveling_api_call_string), R.drawable.transport_placeholder,
                        isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.hiking),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.hiking_api_call_string), R.drawable.hiking_placeholder, isBeingDisplayedInTwoPane)));
        pages.add(new Pair<>(getString(R.string.sports),
                MyViewPagerCategoryFragment.instanceOfWithData(chosenPlace,
                        getString(R.string.sports_api_call_string), R.drawable.sports_placeholder,
                        isBeingDisplayedInTwoPane)));


        mLocationCategoryFragmentPagerAdapter = new LocationCategoryFragmentPagerAdapter(getContext(),
                getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, pages);

        mViewPager.setAdapter(mLocationCategoryFragmentPagerAdapter);




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public int getViewPagerCurrentPage() {
        return mViewPager.getCurrentItem();
    }

    public void moveBackOnePage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_selection_fragment_menu, menu);
        menu.getItem(0).setTitle(getString(R.string.favorite_places));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Context context = getContext();

        switch (item.getItemId()) {

            case R.id.action_go_to_favorites:

                Intent favoritesActivityIntent = new Intent(context, FavoritesActivity.class);

                assert context != null;
                if (favoritesActivityIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(favoritesActivityIntent);
                }
                break;
            case R.id.action_go_to_exchange_rates:

                Intent exchangeRatesActivityIntent = new Intent(context, ExchangeRatesActivity.class);

                assert context != null;
                if (exchangeRatesActivityIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(exchangeRatesActivityIntent);
                }
                break;




        }
        return super.onOptionsItemSelected(item);
    }




}

