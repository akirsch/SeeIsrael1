package com.example.android.seeisrael.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.seeisrael.R;
import com.example.android.seeisrael.interfaces.SygicPlacesApiService;
import com.example.android.seeisrael.models.Media;
import com.example.android.seeisrael.models.Place;
import com.example.android.seeisrael.models.PlaceDetailsMainBodyResponse;
import com.example.android.seeisrael.networking.RetrofitClientInstance;
import com.example.android.seeisrael.utils.Config;
import com.example.android.seeisrael.utils.Constants;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationDetailsFragment extends Fragment {

    private Unbinder mUnbinder;
    private String mSelectedPlaceId;
    private String mSelectedPlaceName;
    private String imageUrlToDisplay;
    private Place selectedPlace;
    private String TAG;

    public LocationDetailsFragment(){}

    private Toolbar mToolbar;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.location_image_view)
    ImageView locationImageView;

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

    @BindView(R.id.watch_video_tv)
    TextView watchMovieTv;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView =  inflater.inflate(R.layout.fragment_location_detail, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        loadingProgressBar.setVisibility(View.VISIBLE);
        noContentToDisplayView.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.GONE);
        detailsLinearLayoutContainer.setVisibility(View.GONE);
        fabButton.setVisibility(View.GONE);




        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        TAG = getActivity().getClass().getSimpleName();

        mSelectedPlaceId = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.SELECTED_PLACE_ID_KEY);
        mSelectedPlaceName = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.SELECTED_PLACE_NAME_KEY);

        // configure the toolbar
        mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);

        // Set the Toolbar as Action Bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("");
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        mToolbar.setPadding(0, 25, 0, 0);


        if (mSelectedPlaceId != null){
            if (Config.hasNetworkConnection(Objects.requireNonNull(getContext()))){
                 getPlaceDetailsFromApi(mSelectedPlaceId);
            }
        }

    }

    public void getPlaceDetailsFromApi(String placeId){

        // replace the ":" in the placeId String with encoded url value due to bug #3080 in Retrofit which
        // is not encoding this symbol automatically
        String encodedPlaceId = placeId.replace(":", "%3A");

        SygicPlacesApiService sygicPlacesApiService = RetrofitClientInstance
                .getRetrofitInstance(getContext())
                .create(SygicPlacesApiService.class);

        final Call<PlaceDetailsMainBodyResponse> placeDetailsCall
                = sygicPlacesApiService.getPlaceDetails(encodedPlaceId);

        placeDetailsCall.enqueue(new Callback<PlaceDetailsMainBodyResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsMainBodyResponse> call, Response<PlaceDetailsMainBodyResponse> response) {

                Log.v("response_log", "Response received from api call");

                if (response.isSuccessful() && response.body() != null){
                    selectedPlace = response.body().data.place;

                    appBarLayout.setVisibility(View.VISIBLE);
                    detailsLinearLayoutContainer.setVisibility(View.VISIBLE);
                    fabButton.setVisibility(View.VISIBLE);
                    loadingProgressBar.setVisibility(View.GONE);
                    noContentToDisplayView.setVisibility(View.GONE);

                    if (selectedPlace.name != null && !selectedPlace.name.isEmpty()){
                        locationTitleTv.setText(selectedPlace.name);
                    }

                    if (selectedPlace.nameSuffix != null && !selectedPlace.nameSuffix.isEmpty()){
                        locationSubtitleTv.setText(selectedPlace.nameSuffix);
                    }

                    if (selectedPlace.description.longDescription != null
                            && !selectedPlace.description.longDescription.isEmpty()) {
                        expandableTextView.setText(selectedPlace.description.longDescription);
                    }

                    // configure behaviour of expandable text view toggle button
                    setUpToggleButton();

                    // get photo of location from array of Media objects to display in imageView
                    imageUrlToDisplay = null;

                    if (selectedPlace.mainMedia.media != null && !selectedPlace.mainMedia.media.isEmpty()){

                        List<Media> mediaList = selectedPlace.mainMedia.media;

                        for (int i = 0; i < mediaList.size(); i++){
                            if (mediaList.get(i).type.contentEquals("photo")){
                                imageUrlToDisplay = mediaList.get(i).mediaUrl;
                                break;
                            }
                        }
                    }

                    setUpImageView(imageUrlToDisplay);

                    if (selectedPlace.phoneNumber != null && !selectedPlace.phoneNumber.isEmpty()){
                        locationPhoneNumberTv.setText(selectedPlace.phoneNumber);
                    } else {
                        locationPhoneNumberTv.setVisibility(View.GONE);
                    }


                    if (selectedPlace.address != null && !selectedPlace.address.isEmpty()){
                        locationAddressTv.setText(selectedPlace.address);
                    } else {
                        locationAddressTv.setVisibility(View.GONE);
                    }

                    if (selectedPlace.email != null && !selectedPlace.email.isEmpty()){
                        locationEmailAddressTv.setText(selectedPlace.email);
                    } else {
                        locationEmailAddressTv.setVisibility(View.GONE);
                    }

                    if (selectedPlace.description.wikiUrl != null
                            && !selectedPlace.description.wikiUrl.isEmpty()){
                        wikipediaLinkTv.setVisibility(View.VISIBLE);
                        setUpWikipediaIntent(selectedPlace.description.wikiUrl);
                    } else {
                        wikipediaLinkTv.setVisibility(View.GONE);
                    }

                    if (selectedPlace.admission != null && !selectedPlace.admission.isEmpty()){
                        ticketInfoTv.setText(selectedPlace.admission);
                    }

                    if (selectedPlace.openingHours != null && !selectedPlace.openingHours.isEmpty()){
                        openingHoursTv.setText(selectedPlace.openingHours);
                    }

                } else {
                    appBarLayout.setVisibility(View.GONE);
                    detailsLinearLayoutContainer.setVisibility(View.GONE);
                    loadingProgressBar.setVisibility(View.GONE);
                    noContentToDisplayView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<PlaceDetailsMainBodyResponse> call, Throwable t) {
                appBarLayout.setVisibility(View.GONE);
                detailsLinearLayoutContainer.setVisibility(View.GONE);
                loadingProgressBar.setVisibility(View.GONE);
                noContentToDisplayView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setUpImageView(String imageUrlToDisplay){

        // display high res picture for this location. If no image is available
        // display placeholder image instead

        RequestOptions requestOptions = new RequestOptions().centerCrop();

        if (imageUrlToDisplay != null){

            Glide.with(Objects.requireNonNull(getContext()))
                    .load(imageUrlToDisplay)
                    .apply(requestOptions)
                    .into(locationImageView);
        } else {
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(R.mipmap.ic_launcher)
                    .apply(requestOptions)
                    .into(locationImageView);
        }
    }

    private void setUpToggleButton(){

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

    private void setUpWikipediaIntent(String wikipediaUrl){
        wikipediaLinkTv.setOnClickListener(v -> {
            Intent wikipediaIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(wikipediaUrl));
            startActivity(wikipediaIntent);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}


