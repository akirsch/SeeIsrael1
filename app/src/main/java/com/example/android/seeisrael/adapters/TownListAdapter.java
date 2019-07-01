package com.example.android.seeisrael.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.seeisrael.R;
import com.example.android.seeisrael.activities.LocationsActivity;
import com.example.android.seeisrael.models.Places;
import com.example.android.seeisrael.utils.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TownListAdapter extends RecyclerView.Adapter<TownListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Places> mPlacesArrayList;

    public TownListAdapter(){}

    @NonNull
    @Override
    public TownListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View townListItemView = layoutInflater.inflate(R.layout.town_list_item, parent, false);

        return new ViewHolder(townListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TownListAdapter.ViewHolder viewHolder, int position) {

        final Places currentPlaces = mPlacesArrayList.get(position);

        String townName = currentPlaces.name;

        String townImageUrl = currentPlaces.thumbnail_url;

        viewHolder.townNameView.setText(townName);

        // display thumbnail image for each town
        RequestOptions requestOptions = new RequestOptions().centerCrop();

        Glide.with(mContext)
                .load(townImageUrl)
                .apply(requestOptions)
                .into(viewHolder.townImageView);

        viewHolder.itemView.setOnClickListener(view -> {

            Context context = view.getContext();

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.SELECTED_PLACES_KEY, currentPlaces);


            Intent chosenLocationIntent = new Intent(context, LocationsActivity.class);
            chosenLocationIntent.putExtras(bundle);


            if (chosenLocationIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(chosenLocationIntent);
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
        @BindView(R.id.town_image_view)
        ImageView townImageView;

        @BindView(R.id.town_name_tv)
        TextView townNameView;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void setTownList (ArrayList<Places> placesList){
        mPlacesArrayList = placesList;
        notifyDataSetChanged();
    }
}
