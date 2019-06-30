package com.example.android.seeisrael.adapters;

import android.content.Context;

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
