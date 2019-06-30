package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Places implements Parcelable {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    public List<String> categories;

    @SerializedName("perex")
    public String summary;

    @SerializedName("thumbnail_url")
    public String thumbnail_url;

    public Places(){}

    public Places(String id, String name, String thumbnail_url) {
        this.id = id;
        this.name = name;
        this.thumbnail_url = thumbnail_url;
    }

    public Places(String name){
        this.name = name;
    }

    public static final Creator<Places> CREATOR = new Creator<Places>() {
        @Override
        public Places createFromParcel(Parcel in) {
            return new Places(in);
        }

        @Override
        public Places[] newArray(int size) {
            return new Places[size];
        }
    };

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

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.categories);
        dest.writeString(this.summary);
        dest.writeString(this.thumbnail_url);
    }

    protected Places(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.categories = in.createStringArrayList();
        this.summary = in.readString();
        this.thumbnail_url = in.readString();
    }

}
