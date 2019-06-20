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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LocationCategoryFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private String[] categoryTitles = { mContext.getString(R.string.discover),
                                        mContext.getString(R.string.sightseeing),
                                        mContext.getString(R.string.eat),
                                        mContext.getString(R.string.shopping),
                                        mContext.getString(R.string.playing),
                                        mContext.getString(R.string.travelling),
                                        mContext.getString(R.string.hiking),
                                        mContext.getString(R.string.sports)};

    private static final int NUMBER_OF_CATEGORIES = 8;

    public LocationCategoryFragmentPagerAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new DiscoverPlacesFragment();
            case 1:
                return new SightSeeingPlacesFragment();
            case 2:
                return new EatingPlacesFragment();
            case 3:
                return new ShoppingPlacesFragment();
            case 4:
                return new FamilyPlacesFragment();
            case 5:
                return new TransportFragment();
            case 6:
                return new HikingPlacesFragment();
            case 7:
                return new SportsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_CATEGORIES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return categoryTitles[position];
    }
}
