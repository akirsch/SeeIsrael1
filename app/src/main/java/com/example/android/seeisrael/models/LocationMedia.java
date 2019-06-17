package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LocationMedia implements Parcelable {

    @SerializedName("id")
    private String mId;

    @SerializedName("type")
    private String mType;


    @SerializedName("url")
    private String mMediaUrl;

    public LocationMedia(String mId, String mType, String mMediaUrl) {
        this.mId = mId;
        this.mType = mType;
        this.mMediaUrl = mMediaUrl;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmMediaUrl() {
        return mMediaUrl;
    }

    public void setmMediaUrl(String mMediaUrl) {
        this.mMediaUrl = mMediaUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mType);
        dest.writeString(this.mMediaUrl);
    }

    protected LocationMedia(Parcel in) {
        this.mId = in.readString();
        this.mType = in.readString();
        this.mMediaUrl = in.readString();
    }

    public static final Parcelable.Creator<LocationMedia> CREATOR = new Parcelable.Creator<LocationMedia>() {
        @Override
        public LocationMedia createFromParcel(Parcel source) {
            return new LocationMedia(source);
        }

        @Override
        public LocationMedia[] newArray(int size) {
            return new LocationMedia[size];
        }
    };
}
