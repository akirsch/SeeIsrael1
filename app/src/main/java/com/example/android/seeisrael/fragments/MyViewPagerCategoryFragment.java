package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.adapters.PlacesListAdapter;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.utils.Constants;
import com.example.android.seeisrael.viewmodels.CategoryLocationListViewModel;
import com.example.android.seeisrael.viewmodels.CategoryLocationListViewModelFactory;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyViewPagerCategoryFragment extends Fragment {

    private Unbinder mUnbinder;
    private ArrayList<Places> mDiscoveryPlacesList;
    private PlacesListAdapter mPlacesListAdapter;
    private Places selectedPlace;
    private String selectedCategory;
    private boolean isBeingDisplayedInTwoPane;
    private int selectedPlaceholderImageId;
    private final int API_RESULT_LIMIT = 50;



    @BindView(R.id.location_list_recycler_view)
    RecyclerView mLocationListRecyclerView;

    @BindView(R.id.loading_spinner)
    ProgressBar mProgressBar;

    @BindView(R.id.empty_list_view)
    TextView mEmptyListView;

    public MyViewPagerCategoryFragment() {
    }

    // method to allow parent Fragment to pass data to child fragment upon initialization
    public static MyViewPagerCategoryFragment instanceOfWithData(Places places, String searchCategory,
                                                                 int placeholderImageId, boolean isInTwoPane) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.SELECTED_PLACES_KEY, places);
        bundle.putString(Constants.SELECTED_CATEGORY_KEY, searchCategory);
        bundle.putInt(Constants.SELECTED_PLACEHOLDER_IMAGE_ID_KEY, placeholderImageId);
        bundle.putBoolean(Constants.IS_TWO_PANE_BOOLEAN_KEY, isInTwoPane);

        MyViewPagerCategoryFragment myViewPagerCategoryFragment = new MyViewPagerCategoryFragment();
        myViewPagerCategoryFragment.setArguments(bundle);
        return myViewPagerCategoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_location_list, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        mLocationListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocationListRecyclerView.hasFixedSize();

        mLocationListRecyclerView.addItemDecoration(Config.createDividerDecorWithMargin(Objects.requireNonNull(getContext())));




        mLocationListRecyclerView.setVisibility(View.GONE);

        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyListView.setVisibility(View.GONE);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();



        if (getArguments() != null) {
            // Get instance of selected Town
            selectedPlace = getArguments().getParcelable(Constants.SELECTED_PLACES_KEY);
            selectedCategory = getArguments().getString(Constants.SELECTED_CATEGORY_KEY);
            selectedPlaceholderImageId = getArguments().getInt(Constants.SELECTED_PLACEHOLDER_IMAGE_ID_KEY);
            isBeingDisplayedInTwoPane = getArguments().getBoolean(Constants.IS_TWO_PANE_BOOLEAN_KEY);

            mPlacesListAdapter = new PlacesListAdapter(getActivity(), selectedPlaceholderImageId, isBeingDisplayedInTwoPane);
            mLocationListRecyclerView.setAdapter(mPlacesListAdapter);

            if (selectedPlace != null) {
                // get Id of selected town to use for call to API
                String selectedPlaceId = selectedPlace.id;

                CategoryLocationListViewModelFactory factory =
                        new CategoryLocationListViewModelFactory(selectedPlaceId, selectedCategory);
                final CategoryLocationListViewModel categoryLocationListViewModel =
                        ViewModelProviders.of(this, factory)
                                .get(CategoryLocationListViewModel.class);

                if (Config.hasNetworkConnection(Objects.requireNonNull(getContext()))) {

                    categoryLocationListViewModel.initialize();

                    categoryLocationListViewModel.getListOfLocationsData()
                            .observe(this, townQueryMainBodyResponse -> {

                                if (townQueryMainBodyResponse != null) {
                                    mDiscoveryPlacesList = (ArrayList<Places>) townQueryMainBodyResponse.data.places;

                                    // pass this list to the adapter
                                    mPlacesListAdapter.setPlacesList(mDiscoveryPlacesList);
                                    mPlacesListAdapter.notifyDataSetChanged();

                                    // handle UI for a successful API call
                                    mLocationListRecyclerView.setVisibility(View.VISIBLE);
                                    mProgressBar.setVisibility(View.GONE);
                                    mEmptyListView.setVisibility(View.GONE);

                                } else {
                                    mLocationListRecyclerView.setVisibility(View.GONE);
                                    mProgressBar.setVisibility(View.GONE);
                                    mEmptyListView.setVisibility(View.VISIBLE);
                                    mEmptyListView.setText(R.string.placeholder_no_locations);
                                }
                            });
                }
            }

        }
    }


}
