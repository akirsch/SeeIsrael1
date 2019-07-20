package com.example.android.seeisrael.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.MainActivity;
import com.example.android.seeisrael.adapters.TownListAdapter;
import com.example.android.seeisrael.interfaces.SygicPlacesApiService;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.models.TownQueryMainBodyResponse;
import com.example.android.seeisrael.networking.RetrofitClientInstance;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.viewmodels.TownListFragmentViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CityListFragment extends Fragment {

    private Unbinder mUnbinder;
    private ArrayList<Places> mPlacesList;
    private TownListAdapter mTownListAdapter;
    private MainActivity mParentActivity;
    private TownListFragmentViewModel townListFragmentViewModel;

    private static final int LANSCAPE_COLUMN_NUMBER = 3;
    private static final int PORTRAIT_COLUMN_NUMBER = 2;

    @BindView(R.id.town_list_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_spinner)
    ProgressBar mProgresBar;

    @BindView(R.id.empty_list_view)
    TextView mEmptyListTextView;

    // mandatory empty constructor
    public CityListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mParentActivity = (MainActivity) getActivity();

        final View rootView = inflater.inflate(R.layout.fragment_town_list, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        // handle initial UI before abd during the API call
        mEmptyListTextView.setVisibility(View.GONE);
        mProgresBar.setVisibility(View.VISIBLE);

        // when activity is being displayed in landscape mode, display recyclerView as a GridView ,
        // with 3 columns, when in portrait mode, display only 2 columns

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mRecyclerView.setLayoutManager(new GridLayoutManager(mParentActivity, LANSCAPE_COLUMN_NUMBER));

        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mParentActivity, PORTRAIT_COLUMN_NUMBER));
        }

        mRecyclerView.hasFixedSize();

        mTownListAdapter = new TownListAdapter();
        mRecyclerView.setAdapter(mTownListAdapter);

        townListFragmentViewModel = ViewModelProviders.of(this).get(TownListFragmentViewModel.class);

        if (Config.hasNetworkConnection(getContext())){

            townListFragmentViewModel.initialize();
            townListFragmentViewModel.getTownData().observe(this, new Observer<TownQueryMainBodyResponse>() {
                @Override
                public void onChanged(TownQueryMainBodyResponse townQueryMainBodyResponse) {
                    if (townQueryMainBodyResponse != null){

                        mPlacesList = (ArrayList<Places>) townQueryMainBodyResponse.data.places;
                        mTownListAdapter.setTownList(mPlacesList);
                        mTownListAdapter.notifyDataSetChanged();

                        // handle UI for a successful API call
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mProgresBar.setVisibility(View.GONE);
                        mEmptyListTextView.setVisibility(View.GONE);


                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        mProgresBar.setVisibility(View.GONE);
                        mEmptyListTextView.setVisibility(View.VISIBLE);
                        mEmptyListTextView.setText(getString(R.string.no_towns_available));
                    }

                }
            });




        } else {
            mRecyclerView.setVisibility(View.GONE);
            mProgresBar.setVisibility(View.GONE);
            mEmptyListTextView.setVisibility(View.VISIBLE);
            mEmptyListTextView.setText(getString(R.string.no_wifi));
        }

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
