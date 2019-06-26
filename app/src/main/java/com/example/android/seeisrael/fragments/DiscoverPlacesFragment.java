package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.adapters.PlacesListAdapter;
import com.example.android.seeisrael.interfaces.SygicPlacesApiService;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.networking.RetrofitClientInstance;
import com.example.android.seeisrael.utils.Config;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverPlacesFragment extends Fragment {

    private Unbinder mUnbinder;
    private ArrayList<Places> mDiscoveryPlacesList;
    private PlacesListAdapter mPlacesListAdapter;
    private Places selectedPlace;


    @BindView(R.id.location_list_recycler_view)
    RecyclerView mLocationListRecyclerView;

    @BindView(R.id.loading_spinner)
    ProgressBar mProgresBar;

    @BindView(R.id.empty_list_view)
    TextView mEmptyListView;

    public DiscoverPlacesFragment() {}

    // method to allow parent Fragment to pass data to child fragment upon initialization
    public static DiscoverPlacesFragment instanceOfWithData(Places places) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("places-key", places);

        DiscoverPlacesFragment discoverPlacesFragment = new DiscoverPlacesFragment();
        discoverPlacesFragment.setArguments(bundle);
        return discoverPlacesFragment;
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

        mPlacesListAdapter = new PlacesListAdapter();
        mLocationListRecyclerView.setAdapter(mPlacesListAdapter);


        mLocationListRecyclerView.setVisibility(View.GONE);

        mProgresBar.setVisibility(View.VISIBLE);
        mEmptyListView.setVisibility(View.GONE);



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            // Get instance of selected Town
            selectedPlace = getArguments().getParcelable("places-key");

            if (selectedPlace != null) {
                // get Id of selected town to use for call to API
                String selectedPlaceId = selectedPlace.id;

                if (Config.hasNetworkConnection(getContext())) {
                    getCategoryRelevantDataFromApi(selectedPlaceId);
                }
            }

        }
    }

    private void getCategoryRelevantDataFromApi(String placeId) {

        SygicPlacesApiService sygicPlacesApiService = RetrofitClientInstance
                .getRetrofitInstance(getContext())
                .create(SygicPlacesApiService.class);

        final Call<TownQueryMainBodyResponse> listOfPlacesInTownCall
                = sygicPlacesApiService.getAllPlacesInTown(placeId);


        listOfPlacesInTownCall.enqueue(new Callback<TownQueryMainBodyResponse>() {
            @Override
            public void onResponse(Call<TownQueryMainBodyResponse> call, Response<TownQueryMainBodyResponse> response) {

                if (response.isSuccessful() && response.body() != null){
                    mDiscoveryPlacesList = (ArrayList<Places>) response.body().data.places;

                    // pass this list to the adapter
                    mPlacesListAdapter.setPlacesList(mDiscoveryPlacesList);
                    mPlacesListAdapter.notifyDataSetChanged();

                    // handle UI for a successful API call
                    mLocationListRecyclerView.setVisibility(View.VISIBLE);
                    mProgresBar.setVisibility(View.GONE);
                    mEmptyListView.setVisibility(View.GONE);




                } else{
                    mLocationListRecyclerView.setVisibility(View.GONE);
                    mProgresBar.setVisibility(View.GONE);
                    mEmptyListView.setVisibility(View.VISIBLE);
                    mEmptyListView.setText(R.string.placeholder_no_locations);
                }

            }

            @Override
            public void onFailure(Call<TownQueryMainBodyResponse> call, Throwable throwable) {
                    mLocationListRecyclerView.setVisibility(View.GONE);
                    mProgresBar.setVisibility(View.GONE);
                    mEmptyListView.setVisibility(View.VISIBLE);
                    mEmptyListView.setText(R.string.placeholder_no_locations);
            }
        });
    }
}
