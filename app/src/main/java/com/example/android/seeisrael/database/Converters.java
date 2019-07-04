package com.example.android.seeisrael.database;

import com.example.android.seeisrael.models.Description;
import com.example.android.seeisrael.models.Location;
import com.example.android.seeisrael.models.MainMedia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static List<String> fromStringToList(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromListToString(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static Location fromStringToLocation (String value) {
        Gson gson = new Gson();
        Location location = gson.fromJson(value, Location.class);
        return location;
    }

    @TypeConverter
    public static String fromLocationToString (Location location){
        Gson gson = new Gson();
        String jsonString = gson.toJson(location);
        return jsonString;

    }

    @TypeConverter
    public static Description fromStringToDescription (String value) {
        Gson gson = new Gson();
        Description description = gson.fromJson(value, Description.class);
        return description;
    }

    @TypeConverter
    public static String fromDescriptionToString (Description description) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(description);
        return jsonString;
    }

    @TypeConverter
    public static MainMedia fromStringToMainMedia (String value) {
        Gson gson = new Gson();
        MainMedia mainMedia = gson.fromJson(value, MainMedia.class);
        return mainMedia;
    }

    @TypeConverter
    public static String fromMainMediaToString (MainMedia mainMedia){
        Gson gson = new Gson();
        String jsonString = gson.toJson(mainMedia);
        return jsonString;
    }


}
