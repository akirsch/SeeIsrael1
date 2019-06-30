package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Place implements Parcelable {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("categories")
    public List<String> categories;

    @SerializedName("perex")
    public String summary;

    @SerializedName("thumbnail_url")
    public String thumbnail_url;

    @SerializedName("location")
    public Location location;

    @SerializedName("name_suffix")
    public String nameSuffix;

    @SerializedName("address")
    public String address;

    @SerializedName("admission")
    public String admission;

    @SerializedName("email")
    public String email;

    @SerializedName("opening_hours")
    public String openingHours;

    @SerializedName("phone")
    public String phoneNumber;

    @SerializedName("description")
    public Description description;

    @SerializedName("main_media")
    public MainMedia mainMedia;


    public Place() {}

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
        dest.writeParcelable(this.location, flags);
        dest.writeString(this.nameSuffix);
        dest.writeString(this.address);
        dest.writeString(this.admission);
        dest.writeString(this.email);
        dest.writeString(this.openingHours);
        dest.writeString(this.phoneNumber);
        dest.writeParcelable(this.description, flags);
        dest.writeParcelable(this.mainMedia, flags);
    }

    protected Place(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.categories = in.createStringArrayList();
        this.summary = in.readString();
        this.thumbnail_url = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.nameSuffix = in.readString();
        this.address = in.readString();
        this.admission = in.readString();
        this.email = in.readString();
        this.openingHours = in.readString();
        this.phoneNumber = in.readString();
        this.description = in.readParcelable(Description.class.getClassLoader());
        this.mainMedia = in.readParcelable(MainMedia.class.getClassLoader());
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
