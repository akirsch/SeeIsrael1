package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainMedia implements Parcelable {

    @SerializedName("media")
    public List<Media> media;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.media);
    }

    public MainMedia() {
    }

    protected MainMedia(Parcel in) {
        this.media = in.createTypedArrayList(Media.CREATOR);
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

