package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.LocationsActivity;
import com.example.android.seeisrael.adapters.LocationCategoryFragmentPagerAdapter;
import com.example.android.seeisrael.adapters.TownListAdapter;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.utils.Constants;
import com.google.android.material.appbar.AppBarLayout;
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
    Places chosenPlace;

    private final List<Pair<String, Fragment>> pages = new ArrayList<>();

    public LocationsCategoriesListsFragment() {
    }

    Toolbar mToolbar;

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

        // get chosen town from the parent Activity of this Fragment
        chosenPlace = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(Constants.SELECTED_PLACES_KEY);

        mToolbar = getActivity().findViewById(R.id.toolbar);

        // Set the Toolbar as Action Bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        // Set title of action bar to appropriate label for this Activity
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(chosenPlace.name);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        mToolbar.setPadding(0, 25, 0, 0);

        pages.add(new Pair<>(getString(R.string.discover), DiscoverPlacesFragment.instanceOfWithData(chosenPlace)));
        pages.add(new Pair<>(getString(R.string.sightseeing), SightSeeingPlacesFragment.instanceOfWithData(chosenPlace)));
        pages.add(new Pair<>(getString(R.string.eat), EatingPlacesFragment.instanceOfWithData(chosenPlace)));
        pages.add(new Pair<>(getString(R.string.shopping), ShoppingPlacesFragment.instanceOfWithData(chosenPlace)));
        pages.add(new Pair<>(getString(R.string.playing), FamilyPlacesFragment.instanceOfWithData(chosenPlace)));
        pages.add(new Pair<>(getString(R.string.travelling), TransportFragment.instanceOfWithData(chosenPlace)));
        pages.add(new Pair<>(getString(R.string.hiking), HikingPlacesFragment.instanceOfWithData(chosenPlace)));
        pages.add(new Pair<>(getString(R.string.sports), SportsFragment.instanceOfWithData(chosenPlace)));


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


}

