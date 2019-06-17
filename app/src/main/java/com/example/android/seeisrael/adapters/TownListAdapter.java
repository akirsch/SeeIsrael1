package com.example.android.seeisrael.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.seeisrael.R;
import com.example.android.seeisrael.models.Town;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TownListAdapter extends RecyclerView.Adapter<TownListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Town> mTownArrayList;

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

        final Town currentTown = mTownArrayList.get(position);

        String townName = currentTown.getName();

        String townImageUrl = currentTown.getThumbnailImageUrl();

        viewHolder.townNameView.setText(townName);

        // display thumbnail image for each town
        RequestOptions requestOptions = new RequestOptions().centerCrop();

        Glide.with(mContext)
                .load(townImageUrl)
                .apply(requestOptions)
                .into(viewHolder.townImageView);

    }

    @Override
    public int getItemCount() {
        if (mTownArrayList == null) return 0;
        else return mTownArrayList.size();
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

    public void setTownList (ArrayList<Town> townList){
        mTownArrayList = townList;
        notifyDataSetChanged();
    }
}
