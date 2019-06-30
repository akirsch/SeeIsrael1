package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public LocationDetailsFragment(){}

    private Toolbar mToolbar;

    @BindView(R.id.location_image_view)
    ImageView locatonImageView;

    @BindView(R.id.location_title_view)
    TextView locationTitleTv;

    @BindView(R.id.location_subtitle_tv)
    TextView locationSubtitleTv;

    @BindView(R.id.expandableTextView)
    ExpandableTextView locationDescription;

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

    @BindView(R.id.content_cant_be_displayed_view)
    TextView noContentToDisplayView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView =  inflater.inflate(R.layout.fragment_location_detail, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        noContentToDisplayView.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSelectedPlaceId = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.SELECTED_PLACE_ID_KEY);
        mSelectedPlaceName = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.SELECTED_PLACE_NAME_KEY);

        // configure the toolbar
        mToolbar = getActivity().findViewById(R.id.toolbar);

        // Set the Toolbar as Action Bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        // Set title of action bar to appropriate label for this Activity
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(mSelectedPlaceName);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        mToolbar.setPadding(0, 25, 0, 0);

        Log.v("api_request_string", Constants.SYGIC_API_BASE_URL + mSelectedPlaceId );
        getPlaceDetailsFromApi(mSelectedPlaceId);

//        if (mSelectedPlaceId != null){
//            if (Config.hasNetworkConnection(Objects.requireNonNull(getContext()))){
//
//            }
//        }

    }

    public void getPlaceDetailsFromApi(String placeId){

        SygicPlacesApiService sygicPlacesApiService = RetrofitClientInstance
                .getRetrofitInstance(getContext())
                .create(SygicPlacesApiService.class);

        final Call<PlaceDetailsMainBodyResponse> placeDetailsCall
                = sygicPlacesApiService.getPlaceDetails(placeId);

        placeDetailsCall.enqueue(new Callback<PlaceDetailsMainBodyResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsMainBodyResponse> call, Response<PlaceDetailsMainBodyResponse> response) {

                Log.v("response_log", "Response received from api call");

                if (response.isSuccessful() && response.body() != null){
                    selectedPlace = response.body().data.place;

                    detailsLinearLayoutContainer.setVisibility(View.VISIBLE);
                    noContentToDisplayView.setVisibility(View.GONE);

                    if (selectedPlace.name != null && !selectedPlace.name.isEmpty()){
                        locationTitleTv.setText(selectedPlace.name);
                    }

                    if (selectedPlace.nameSuffix != null && !selectedPlace.nameSuffix.isEmpty()){
                        locationSubtitleTv.setText(selectedPlace.nameSuffix);
                    }

                    if (selectedPlace.description.longDescription != null
                            && !selectedPlace.description.longDescription.isEmpty()) {
                        locationDescription.setText(selectedPlace.description.longDescription);
                    }

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

                    locationPhoneNumberTv.setText(selectedPlace.phoneNumber);

                    locationAddressTv.setText(selectedPlace.address);

                    locationEmailAddressTv.setText(selectedPlace.email);

                    if (selectedPlace.description.wikiUrl != null
                            && !selectedPlace.description.wikiUrl.isEmpty()){
                        wikipediaLinkTv.setText(selectedPlace.description.wikiUrl);
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
                    detailsLinearLayoutContainer.setVisibility(View.GONE);
                    noContentToDisplayView.setVisibility(View.VISIBLE);
                    Log.v("error_log_tag", "else block called in api response");
                }

            }

            @Override
            public void onFailure(Call<PlaceDetailsMainBodyResponse> call, Throwable t) {
                detailsLinearLayoutContainer.setVisibility(View.GONE);
                noContentToDisplayView.setVisibility(View.VISIBLE);
                Log.v("error_log", "onFail called in retrofit call back");
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
                    .into(locatonImageView);
        } else {
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(R.mipmap.ic_launcher)
                    .apply(requestOptions)
                    .into(locatonImageView);
        }
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


