package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.adapters.FavoriteListAdapter;
import com.example.android.seeisrael.database.PlacesDatabase;
import com.example.android.seeisrael.models.Place;
import com.example.android.seeisrael.utils.AppExecutors;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.viewmodels.FavoritesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoritesFragment extends Fragment {

    private Unbinder mUnbinder;
    private ArrayList<Place> mFavotitePlacesList;
    private FavoriteListAdapter mFavoritePlacesListAdapter;
    private PlacesDatabase mDb;

    @BindView(R.id.location_list_recycler_view)
    RecyclerView mLocationListRecyclerView;

    @BindView(R.id.loading_spinner)
    ProgressBar mProgressBar;

    @BindView(R.id.empty_list_view)
    TextView mEmptyListView;

    public FavoritesFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_location_list, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLocationListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocationListRecyclerView.hasFixedSize();

        mLocationListRecyclerView.addItemDecoration(Config.createDividerDecorWithMargin(Objects.requireNonNull(getContext())));

        mFavoritePlacesListAdapter = new FavoriteListAdapter();
        mLocationListRecyclerView.setAdapter(mFavoritePlacesListAdapter);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Place> places = mFavoritePlacesListAdapter.getFavoritePlaces();
                        mDb.favoritePlacesDao().deletePlace(places.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mLocationListRecyclerView);


        mLocationListRecyclerView.setVisibility(View.VISIBLE);

        mProgressBar.setVisibility(View.GONE);
        mEmptyListView.setVisibility(View.GONE);


        mDb = PlacesDatabase.getInstance(getContext());
        setUpViewModel();

    }


    private void setUpViewModel(){

        FavoritesViewModel viewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        viewModel.getFavoritePlaces().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                mFavoritePlacesListAdapter.setPlacesList(places);
                mFavoritePlacesListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
