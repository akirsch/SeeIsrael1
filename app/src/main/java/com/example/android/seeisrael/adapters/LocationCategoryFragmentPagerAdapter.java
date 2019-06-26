package com.example.android.seeisrael.adapters;

import android.content.Context;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.fragments.DiscoverPlacesFragment;
import com.example.android.seeisrael.fragments.EatingPlacesFragment;
import com.example.android.seeisrael.fragments.FamilyPlacesFragment;
import com.example.android.seeisrael.fragments.HikingPlacesFragment;
import com.example.android.seeisrael.fragments.ShoppingPlacesFragment;
import com.example.android.seeisrael.fragments.SightSeeingPlacesFragment;
import com.example.android.seeisrael.fragments.SportsFragment;
import com.example.android.seeisrael.fragments.TransportFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LocationCategoryFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<Pair<String, Fragment>> mPages;

    public LocationCategoryFragmentPagerAdapter(Context context, @NonNull FragmentManager fm,
                                                int behavior, List<Pair<String, Fragment>> pages) {

        super(fm, behavior);
        mPages = pages;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {


                return mPages.get(position).second;
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mPages.get(position).first;
    }
}
