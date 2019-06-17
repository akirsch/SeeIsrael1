package com.example.android.seeisrael.models;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Town implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail_url")
    private String thumbnailImageUrl;

    public Town(String id, String name, String thumbnailImageUrl) {
        this.id = id;
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public Town (String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.thumbnailImageUrl);
    }

    protected Town(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.thumbnailImageUrl = in.readString();
    }

    public static final Parcelable.Creator<Town> CREATOR = new Parcelable.Creator<Town>() {
        @Override
        public Town createFromParcel(Parcel source) {
            return new Town(source);
        }

        @Override
        public Town[] newArray(int size) {
            return new Town[size];
        }
    };
}
