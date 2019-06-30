package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleLocationData implements Parcelable {

    @SerializedName("place")
    public Place place;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.place, flags);
    }

    public SingleLocationData() {
    }

    protected SingleLocationData(Parcel in) {
        this.place = in.readParcelable(Place.class.getClassLoader());
    }

    public static final Parcelable.Creator<SingleLocationData> CREATOR = new Parcelable.Creator<SingleLocationData>() {
        @Override
        public SingleLocationData createFromParcel(Parcel source) {
            return new SingleLocationData(source);
        }

        @Override
        public SingleLocationData[] newArray(int size) {
            return new SingleLocationData[size];
        }
    };
}
