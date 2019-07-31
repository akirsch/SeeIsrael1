package com.example.android.seeisrael.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.ExchangeRatesActivity;
import com.example.android.seeisrael.activities.FavoritesActivity;
import com.example.android.seeisrael.activities.MapsActivity;
import com.example.android.seeisrael.database.PlacesDatabase;
import com.example.android.seeisrael.models.Media;
import com.example.android.seeisrael.models.Place;
import com.example.android.seeisrael.utils.AppExecutors;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.utils.Constants;
import com.example.android.seeisrael.viewmodels.LocationDetailsViewModel;
import com.example.android.seeisrael.viewmodels.LocationDetailsViewModelFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LocationDetailsFragment extends Fragment {

    private Unbinder mUnbinder;
    private String mSelectedPlaceId;
    private String mSelectedPlaceName;
    private boolean isInTwoPane;
    private String imageUrlToDisplay;
    private Place selectedPlace;
    private String TAG;
    private PlacesDatabase mDb;

    public LocationDetailsFragment() {
    }

    private Toolbar mToolbar;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.location_image_view)
    ImageView locationImageView;

    @BindView(R.id.default_placeholder_place_image_icon)
    ImageView defaultNoPicToDisplayIcon;

    @BindView(R.id.location_title_view)
    TextView locationTitleTv;

    @BindView(R.id.location_subtitle_tv)
    TextView locationSubtitleTv;

    @BindView(R.id.expandableTextView)
    ExpandableTextView expandableTextView;

    @BindView(R.id.button_toggle)
    Button buttonToggle;

    @BindView(R.id.location_phone_number_tv)
    TextView locationPhoneNumberTv;

    @BindView(R.id.location_address_tv)
    TextView locationAddressTv;

    @BindView(R.id.email_address_tv)
    TextView locationEmailAddressTv;

    @BindView(R.id.wikipedia_link_tv)
    TextView wikipediaLinkTv;

    @BindView(R.id.opening_hours_tv)
    TextView openingHoursTv;

    @BindView(R.id.ticket_info_tv)
    TextView ticketInfoTv;

    @BindView(R.id.place_details_linear_layout)
    LinearLayout detailsLinearLayoutContainer;

    @BindView(R.id.loading_spinner)
    ProgressBar loadingProgressBar;

    @BindView(R.id.content_cant_be_displayed_view)
    TextView noContentToDisplayView;

    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_location_detail, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        loadingProgressBar.setVisibility(View.VISIBLE);
        noContentToDisplayView.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.GONE);
        detailsLinearLayoutContainer.setVisibility(View.GONE);
        fabButton.hide();
        defaultNoPicToDisplayIcon.setVisibility(View.GONE);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        TAG = Objects.requireNonNull(getActivity()).getClass().getSimpleName();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        // if this fragment was created with arguments, it is being displayed in 2 pane mode on a tablet
        // within the LocationsActivity
        if (getArguments() != null){
            mSelectedPlaceId = getArguments().getString(Constants.SELECTED_PLACE_ID_KEY);
            mSelectedPlaceName = getArguments().getString(Constants.SELECTED_PLACE_NAME_KEY);
            isInTwoPane = getArguments().getBoolean(Constants.IS_TWO_PANE_BOOLEAN_KEY);

        // otherwise, the fragment is being displayed on its own within a separate LocationsDetailsActivity
        } else {
            mSelectedPlaceId = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.SELECTED_PLACE_ID_KEY);
            mSelectedPlaceName = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.SELECTED_PLACE_NAME_KEY);
            isInTwoPane = false;
        }



        // configure the toolbar
        mToolbar = getActivity().findViewById(R.id.toolbar);


        // Set the Toolbar as Action Bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        // if app is in landscape mode on a phone, set title of toolbar
        if (getActivity().findViewById(R.id.phone_landscape_linear_layout) != null){
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar())
                    .setTitle(mSelectedPlaceName);
        } else{
            // Toolbar shouldn't display title as this is shown in textView in layout
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("");
        }

        // enable up navigation
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        // Set paddingTop of toolbar to height of status bar.
        // Fixes statusbar covers toolbar issue
        mToolbar.setPadding(0, 25, 0, 0);


        if (mSelectedPlaceId != null) {
            if (Config.hasNetworkConnection(Objects.requireNonNull(getContext()))) {

                // replace the ":" in the placeId String with encoded url value due to bug #3080 in Retrofit which
                // is not encoding this symbol automatically
                String encodedPlaceId = mSelectedPlaceId.replace(":", "%3A");

                LocationDetailsViewModelFactory factory = new LocationDetailsViewModelFactory(encodedPlaceId);

                final LocationDetailsViewModel locationDetailsViewModel =
                        ViewModelProviders.of(this, factory)
                                .get(LocationDetailsViewModel.class);

                locationDetailsViewModel.initialize();
                locationDetailsViewModel.getLocationDetailsData()
                        .observe(this, placeDetailsMainBodyResponse -> {

                            if (placeDetailsMainBodyResponse != null) {

                                selectedPlace = placeDetailsMainBodyResponse.data.place;

                                setUpDetailsUI();

                            } else {
                                appBarLayout.setVisibility(View.GONE);
                                detailsLinearLayoutContainer.setVisibility(View.GONE);
                                loadingProgressBar.setVisibility(View.GONE);
                                fabButton.hide();
                                noContentToDisplayView.setVisibility(View.VISIBLE);
                            }


                        });


                mDb = PlacesDatabase.getInstance(getContext());


            }
        }

        locationAddressTv.setOnClickListener(v -> {
            // handle click on address of location by starting Map Activity with intent
            if (selectedPlace.location != null) {
                onAddressClicked();
            }
        });

        fabButton.setOnClickListener(v -> {
            // when user licks on fab button, start calendar app to let user add a visit
            // to this location to their calendar
            if (selectedPlace.name != null) {

                Intent calendarIntent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, selectedPlace.name)
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Visit to " + selectedPlace.name);

                if (selectedPlace.address != null) {
                    calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, selectedPlace.address);
                }
                startActivity(calendarIntent);

            }
        });


    }

    private void setUpDetailsUI() {

        appBarLayout.setVisibility(View.VISIBLE);
        detailsLinearLayoutContainer.setVisibility(View.VISIBLE);
        fabButton.show();
        loadingProgressBar.setVisibility(View.GONE);
        noContentToDisplayView.setVisibility(View.GONE);

        if (selectedPlace.name != null && !selectedPlace.name.isEmpty()) {
            locationTitleTv.setText(selectedPlace.name);
        }

        if (selectedPlace.nameSuffix != null && !selectedPlace.nameSuffix.isEmpty()) {
            locationSubtitleTv.setText(selectedPlace.nameSuffix);
        }
        // check that description object is not Null before referencing its fields
        if (selectedPlace.description != null) {
            if (selectedPlace.description.longDescription != null
                    && !selectedPlace.description.longDescription.isEmpty()) {
                expandableTextView.setText(selectedPlace.description.longDescription);
            } else {
                expandableTextView.setVisibility(View.GONE);
                buttonToggle.setVisibility(View.GONE);
            }

            if (selectedPlace.description.wikiUrl != null
                    && !selectedPlace.description.wikiUrl.isEmpty()) {
                wikipediaLinkTv.setVisibility(View.VISIBLE);
                setUpWikipediaIntent(selectedPlace.description.wikiUrl);
            } else {
                wikipediaLinkTv.setVisibility(View.GONE);
            }
        }


        // configure behaviour of expandable text view toggle button
        setUpToggleButton();

        // get photo of location from array of Media objects to display in imageView
        imageUrlToDisplay = null;
        if (selectedPlace.mainMedia != null) {
            if (selectedPlace.mainMedia.media != null && !selectedPlace.mainMedia.media.isEmpty()) {

                List<Media> mediaList = selectedPlace.mainMedia.media;

                for (int i = 0; i < mediaList.size(); i++) {
                    if (mediaList.get(i).type.contentEquals("photo")) {
                        imageUrlToDisplay = mediaList.get(i).mediaUrl;
                        break;
                    }
                }
            }
        }


        setUpImageView(imageUrlToDisplay);

        if (selectedPlace.phoneNumber != null && !selectedPlace.phoneNumber.isEmpty()) {
            locationPhoneNumberTv.setText(selectedPlace.phoneNumber);
        } else {
            locationPhoneNumberTv.setVisibility(View.GONE);
        }


        if (selectedPlace.address != null && !selectedPlace.address.isEmpty()) {
            locationAddressTv.setText(selectedPlace.address);

            // set text to appear underlined to hint to user that it can be clicked on
            locationAddressTv.setPaintFlags(locationAddressTv
                    .getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            locationAddressTv.setVisibility(View.GONE);
        }

        if (selectedPlace.email != null && !selectedPlace.email.isEmpty()) {
            locationEmailAddressTv.setText(selectedPlace.email);
        } else {
            locationEmailAddressTv.setVisibility(View.GONE);
        }


        if (selectedPlace.admission != null && !selectedPlace.admission.isEmpty()) {
            ticketInfoTv.setText(selectedPlace.admission);
        } else {
            ticketInfoTv.setVisibility(View.GONE);
        }

        if (selectedPlace.openingHours != null && !selectedPlace.openingHours.isEmpty()) {
            openingHoursTv.setText(selectedPlace.openingHours);
        } else {
            openingHoursTv.setVisibility(View.GONE);
        }

    }


    private void setUpImageView(String imageUrlToDisplay) {

        // display high res picture for this location. If no image is available
        // display placeholder image instead

        RequestOptions requestOptions = new RequestOptions().centerCrop();

        if (imageUrlToDisplay != null) {

            Glide.with(Objects.requireNonNull(getContext()))
                    .load(imageUrlToDisplay)
                    .apply(requestOptions)
                    .into(locationImageView);
        } else {
           locationImageView.setVisibility(View.GONE);
           defaultNoPicToDisplayIcon.setVisibility(View.VISIBLE);
        }
    }

    private void setUpToggleButton() {

        expandableTextView.setInterpolator(new OvershootInterpolator());

        buttonToggle.setOnClickListener(v -> {
            buttonToggle.setText(expandableTextView.isExpanded() ? R.string.see_more : R.string.see_less);
            expandableTextView.toggle();
        });

        expandableTextView.addOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(@NonNull ExpandableTextView view) {
                Log.d(TAG, "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(@NonNull ExpandableTextView view) {
                Log.d(TAG, "ExpandableTextView collapsed");
            }
        });
    }

    private void setUpWikipediaIntent(String wikipediaUrl) {
        wikipediaLinkTv.setOnClickListener(v -> {
            Intent wikipediaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikipediaUrl));
            startActivity(wikipediaIntent);
        });
    }

    /**
     * This method starts Map Activity to show current location on a Map with marker
     */
    private void onAddressClicked() {

        Intent mapIntent = new Intent(getContext(), MapsActivity.class);

        // create new LatLng object to store locations co-ordinates
        LatLng locationCoordinates =
                new LatLng(selectedPlace.location.latitude,
                        selectedPlace.location.longitude);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.SELECTED_PLACE_COORDINATES_KEY, locationCoordinates);
        if (selectedPlace.name != null && !selectedPlace.name.isEmpty()) {
            bundle.putString(Constants.SELECTED_PLACE_NAME_KEY, selectedPlace.name);
        }
        // add this data to intent to send to the maps activity
        mapIntent.putExtras(bundle);

        startActivity(mapIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        FragmentManager fragmentManager =
                Objects.requireNonNull(getActivity()).getSupportFragmentManager();

        Context context = getContext();

        switch (item.getItemId()) {
            case R.id.favorites:
                if (Build.VERSION.SDK_INT >= 26) {
                    item.setContentDescription(getString(R.string.favorite_button_content_description));
                }
                onFavoriteIconClicked(item);
                break;
            case R.id.action_go_to_favorites:

                if (isInTwoPane){
                    FavoritesFragment favoritesFragment = new FavoritesFragment();

                    fragmentManager.beginTransaction()
                            .replace(R.id.location_category_selection_fragment_container, favoritesFragment)
                            .commit();

                } else {
                    Intent favoritesActivityIntent = new Intent(context, FavoritesActivity.class);

                    if (favoritesActivityIntent.resolveActivity(Objects.requireNonNull(context).getPackageManager()) != null) {
                        context.startActivity(favoritesActivityIntent);
                    }
                }
                break;
            case R.id.action_go_to_exchange_rates:

                if (isInTwoPane){
                    ExchangeRatesFragment exchangeRatesFragment = new ExchangeRatesFragment();

                    fragmentManager.beginTransaction()
                            .replace(R.id.location_detail_fragment_container, exchangeRatesFragment)
                            .commit();
                }

                Intent exchangeRatesActivityIntent = new Intent(context, ExchangeRatesActivity.class);

                if (exchangeRatesActivityIntent.resolveActivity(Objects.requireNonNull(context).getPackageManager()) != null) {
                    context.startActivity(exchangeRatesActivityIntent);
                }
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.place_details_fragment_menu, menu);
        menu.getItem(0).setTitle(getString(R.string.favorite_places));

    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {

        super.onPrepareOptionsMenu(menu);


        AppExecutors.getInstance().diskIO().execute(() -> {

            final MenuItem favoritesIcon = menu.getItem(1);
            Place place = mDb.favoritePlacesDao().loadPlaceById(mSelectedPlaceId);

            if (place == null) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    Drawable notSelectedAsFavoriteIcon = Objects.requireNonNull(getContext())
                            .getDrawable(R.drawable.ic_favorite_border);
                    assert notSelectedAsFavoriteIcon != null;
                    notSelectedAsFavoriteIcon.setTint(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    favoritesIcon.setIcon(notSelectedAsFavoriteIcon);
                });
            } else {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    Drawable selectedAsFavoriteIcon = Objects.requireNonNull(getContext())
                            .getDrawable(R.drawable.ic_favorite);
                    assert selectedAsFavoriteIcon != null;
                    selectedAsFavoriteIcon.setTint(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    favoritesIcon.setIcon(selectedAsFavoriteIcon);
                });

            }
        });

    }

    private void onFavoriteIconClicked(final MenuItem item) {

        AppExecutors.getInstance().diskIO().execute(() -> {

            final Place place = mDb.favoritePlacesDao().loadPlaceById(mSelectedPlaceId);

            if (place == null) {

                mDb.favoritePlacesDao().insertPlace(selectedPlace);

                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    // UI changes to show full favorites icon once Place has been added to favorites
                    Drawable selectedAsFavoriteIcon = Objects.requireNonNull(getContext())
                            .getDrawable(R.drawable.ic_favorite);
                    assert selectedAsFavoriteIcon != null;
                    selectedAsFavoriteIcon.setTint(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    item.setIcon(selectedAsFavoriteIcon);
                });
            } else {
                // If movie was previously listed a favorite,
                // when favorites button is clicked remove this movie from the favorite movie database
                mDb.favoritePlacesDao().deletePlace(place);

                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    // UI changes to show empty favorites icon now that user has removed it from database
                    Drawable notSelectedAsFavoriteIcon = Objects.requireNonNull(getContext())
                            .getDrawable(R.drawable.ic_favorite_border);
                    assert notSelectedAsFavoriteIcon != null;
                    notSelectedAsFavoriteIcon.setTint(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    item.setIcon(notSelectedAsFavoriteIcon);
                });

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void setPlaceId(String id){
        this.mSelectedPlaceId = id;
    }
}


