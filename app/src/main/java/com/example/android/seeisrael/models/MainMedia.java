package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainMedia implements Parcelable {

    @SerializedName("main_media")
    private ArrayList<LocationMedia> mMediaObjectArray;

    public MainMedia(ArrayList<LocationMedia> mMediaObjectArray) {
        this.mMediaObjectArray = mMediaObjectArray;
    }

    public ArrayList<LocationMedia> getmMediaObjectArray() {
        return mMediaObjectArray;
    }

    public void setmMediaObjectArray(ArrayList<LocationMedia> mMediaObjectArray) {
        this.mMediaObjectArray = mMediaObjectArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mMediaObjectArray);
    }

    protected MainMedia(Parcel in) {
        this.mMediaObjectArray = in.createTypedArrayList(LocationMedia.CREATOR);
    }

    public static final Parcelable.Creator<MainMedia> CREATOR = new Parcelable.Creator<MainMedia>() {
        @Override
        public MainMedia createFromParcel(Parcel source) {
            return new MainMedia(source);
        }

        @Override
        public MainMedia[] newArray(int size) {
            return new MainMedia[size];
        }
    };
}
