package com.example.android.seeisrael.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public class Config {

    public static boolean hasNetworkConnection(Context context){

        // check there is a network connection
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();


        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());

    }
}
