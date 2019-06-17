package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GeoPosition implements Parcelable {

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lng")
    private String longitude;

    public GeoPosition(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected GeoPosition(Parcel in) {
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Parcelable.Creator<GeoPosition> CREATOR = new Parcelable.Creator<GeoPosition>() {
        @Override
        public GeoPosition createFromParcel(Parcel source) {
            return new GeoPosition(source);
        }

        @Override
        public GeoPosition[] newArray(int size) {
            return new GeoPosition[size];
        }
    };
}
