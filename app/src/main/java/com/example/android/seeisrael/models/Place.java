package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_places" )
public class Place implements Parcelable {


    @PrimaryKey (autoGenerate = true)
    @Expose(serialize = false, deserialize = false)
    public int uniqueDatabaseId;

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

    @ColumnInfo(name = "name_suffix")
    @SerializedName("name_suffix")
    public String nameSuffix;

    @SerializedName("address")
    public String address;

    @SerializedName("admission")
    public String admission;

    @SerializedName("email")
    public String email;

    @ColumnInfo(name = "opening_hours")
    @SerializedName("opening_hours")
    public String openingHours;

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone")
    public String phoneNumber;

    @SerializedName("description")
    public Description description;

    @SerializedName("main_media")
    public MainMedia mainMedia;

    public Place(int uniqueDatabaseId, String id, String name, List<String> categories,
                 String summary, String thumbnail_url, Location location, String nameSuffix,
                 String address, String admission, String email, String openingHours,
                 String phoneNumber, Description description, MainMedia mainMedia) {
        this.uniqueDatabaseId = uniqueDatabaseId;
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.summary = summary;
        this.thumbnail_url = thumbnail_url;
        this.location = location;
        this.nameSuffix = nameSuffix;
        this.address = address;
        this.admission = admission;
        this.email = email;
        this.openingHours = openingHours;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.mainMedia = mainMedia;
    }

    @Ignore
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

    public static final Creator<Place> CREATOR = new Creator<Place>() {
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
