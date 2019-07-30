package com.example.android.seeisrael.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.LocationDetailsActivity;
import com.example.android.seeisrael.activities.LocationsActivity;
import com.example.android.seeisrael.fragments.LocationDetailsFragment;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.utils.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Places> mPlacesArrayList;
    private int placeholderImageId;
    private boolean mIsInTwoPane;

    public PlacesListAdapter(Context context, int placeholderImageId, boolean isInTwoPane){

        this.placeholderImageId = placeholderImageId;
        this.mContext = context;
        this.mIsInTwoPane = isInTwoPane;
    }



    @NonNull
    @Override
    public PlacesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View placesListItemView  = layoutInflater.inflate(R.layout.location_list_item, parent, false);

        return new ViewHolder(placesListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesListAdapter.ViewHolder viewHolder, int position) {

        final Places currentPlaces = mPlacesArrayList.get(position);


        final String currentPlaceId = currentPlaces.id;
        final String currentPlaceName = currentPlaces.name;

        String placeName= currentPlaces.name;

        String placeSummary = currentPlaces.summary;

        String placeThumbnailImageUrl = currentPlaces.thumbnail_url;

        if (placeName != null && !placeName.isEmpty()){
            viewHolder.placeTitleView.setText(placeName);
        }

        if (placeSummary != null && !placeSummary.isEmpty()){
            viewHolder.placeSummaryView.setText(placeSummary);
        } else {
            viewHolder.placeSummaryView.setVisibility(View.GONE);
        }

        if (placeThumbnailImageUrl != null && !placeThumbnailImageUrl.isEmpty()){
            // display avatar thumbnail image for each location
            RequestOptions requestOptions = new RequestOptions().centerCrop();

            Glide.with(mContext)
                    .load(placeThumbnailImageUrl)
                    .placeholder(ContextCompat.getDrawable(mContext, placeholderImageId))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .dontAnimate()
                    .apply(requestOptions)
                    .into(viewHolder.circleThumbnailImageView);
            // if no image exists for this location, just display the category specific placeholder image
        } else {
            Glide.with(mContext)
                    .load(placeholderImageId)
                    .into(viewHolder.circleThumbnailImageView);
        }

        viewHolder.itemView.setOnClickListener(view -> {

            Context context = view.getContext();


            viewHolder.circleThumbnailImageView.setTransitionName(context.getString(R.string.transition_image));


            Bundle bundle = new Bundle();
            bundle.putString(Constants.SELECTED_PLACE_ID_KEY, currentPlaceId);
            bundle.putString(Constants.SELECTED_PLACE_NAME_KEY, currentPlaceName);

            // if fragment is being displayed in two pane mode, replace the content of the location
            // detail container with the newly selected location details
            if (mIsInTwoPane){
                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                LocationDetailsFragment locationDetailsFragment = new LocationDetailsFragment();
                bundle.putBoolean(Constants.IS_TWO_PANE_BOOLEAN_KEY, mIsInTwoPane);
                locationDetailsFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.location_detail_fragment_container, locationDetailsFragment)
                        .commit();
                // otherwise, start a new activity to display the details fragment
            } else {
                Intent placeDetailsIntent = new Intent(context, LocationDetailsActivity.class);
                placeDetailsIntent.putExtras(bundle);

                if (placeDetailsIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(placeDetailsIntent);
                }
            }


        });


    }

    @Override
    public int getItemCount() {
        if (mPlacesArrayList == null) return 0;
        else return mPlacesArrayList.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder{

       public final View view;

       @BindView(R.id.thumbnail_image)
       CircleImageView circleThumbnailImageView;

       @BindView(R.id.list_item_primary_textView)
       TextView placeTitleView;

       @BindView(R.id.list_item_secondary_textView)
       TextView placeSummaryView;


       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           view = itemView;
           ButterKnife.bind(this, itemView);
       }
   }

   public void setPlacesList (ArrayList<Places> placesList){
        mPlacesArrayList = placesList;
        notifyDataSetChanged();


   }


}
