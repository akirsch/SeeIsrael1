package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.LocationsActivity;
import com.example.android.seeisrael.adapters.LocationCategoryFragmentPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocationsCategoriesListsFragment extends Fragment {

    private Unbinder mUnbinder;
    private LocationCategoryFragmentPagerAdapter mLocationCategoryFragmentPagerAdapter;
    private LocationsActivity mParentActivity;
    public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;

    public LocationsCategoriesListsFragment(){}


    Toolbar mToolbar;

    @BindView(R.id.tab_layout)
    TabLayout mTablayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_location_category_selection, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);


        mToolbar = getActivity().findViewById(R.id.toolbar);

        // Set the Toolbar as Action Bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        // Set title of action bar to appropriate label for this Activity
        Objects.requireNonNull(((AppCompatActivity)getActivity()).getSupportActionBar()).setTitle(R.string.app_name);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        mToolbar.setPadding(0, 25, 0, 0);

        mLocationCategoryFragmentPagerAdapter = new LocationCategoryFragmentPagerAdapter(getContext(),
                getChildFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(mLocationCategoryFragmentPagerAdapter);

        mTablayout.setupWithViewPager(mViewPager);




        return rootView;
    }
}
