package com.example.android.seeisrael.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.LocationDetailsActivity;
import com.example.android.seeisrael.models.Place;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {

    private Context mContext;
    private List<Place> mFavoritePlacesArrayList;

    public FavoriteListAdapter(){}


    @NonNull
    @Override
    public FavoriteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View placesListItemView  = layoutInflater.inflate(R.layout.location_list_item, parent, false);

        return new ViewHolder(placesListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.ViewHolder viewHolder, int position) {

        final Place currentPlace = mFavoritePlacesArrayList.get(position);

        final String currentPlaceId = currentPlace.id;
        final String currentPlaceName = currentPlace.name;

        String placeName= currentPlace.name;

        String placeSummary = currentPlace.summary;

        String placeThumbnailImageUrl = currentPlace.thumbnail_url;

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
                    .apply(requestOptions)
                    .into(viewHolder.circleThumbnailImageView);
        }

        viewHolder.itemView.setOnClickListener(view -> {

            Context context = view.getContext();


            viewHolder.circleThumbnailImageView.setTransitionName(context.getString(R.string.transition_image));

            Bundle bundle = new Bundle();
            bundle.putString(Constants.SELECTED_PLACE_ID_KEY, currentPlaceId);
            bundle.putString(Constants.SELECTED_PLACE_NAME_KEY, currentPlaceName);

            Intent placeDetailsIntent = new Intent(context, LocationDetailsActivity.class);
            placeDetailsIntent.putExtras(bundle);

            if (placeDetailsIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(placeDetailsIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mFavoritePlacesArrayList == null) return 0;
        else return mFavoritePlacesArrayList.size();
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

    public void setPlacesList (List<Place> placesList){
        mFavoritePlacesArrayList = placesList;
        notifyDataSetChanged();

    }

    public List<Place> getFavoritePlaces() { return mFavoritePlacesArrayList; }
}
