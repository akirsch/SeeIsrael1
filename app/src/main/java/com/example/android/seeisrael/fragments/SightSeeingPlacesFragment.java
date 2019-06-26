package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.models.Places;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SightSeeingPlacesFragment extends Fragment {

    private Unbinder mUnbinder;

    @BindView(R.id.location_list_recycler_view)
    RecyclerView mLocationListRecyclerView;

    @BindView(R.id.loading_spinner)
    ProgressBar mProgresBar;

    @BindView(R.id.empty_list_view)
    TextView mTestView;

    public SightSeeingPlacesFragment(){}

    // method to allow parent Fragment to pass data to child fragment upon initialization
    public static SightSeeingPlacesFragment instanceOfWithData (Places places){
        Bundle bundle = new Bundle();
        bundle.putParcelable("places-key", places);

        SightSeeingPlacesFragment sightSeeingPlacesFragment = new SightSeeingPlacesFragment();
        sightSeeingPlacesFragment.setArguments(bundle);
        return sightSeeingPlacesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView  = inflater.inflate(R.layout.fragment_location_list, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        mLocationListRecyclerView.setVisibility(View.GONE);

        mTestView.setVisibility(View.VISIBLE);
        mTestView.setText("This is a test of the viewpager");

        return rootView;
    }
}
