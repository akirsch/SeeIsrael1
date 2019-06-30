package com.example.android.seeisrael.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.seeisrael.R;

import java.util.Objects;

import androidx.recyclerview.widget.DividerItemDecoration;

public class Config {

    public static boolean hasNetworkConnection(Context context){

        // check there is a network connection
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();


        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());

    }

    public static DividerItemDecoration createDividerDecorWithMargin(Context context){

        int[] ATTRS = new int[]{android.R.attr.listDivider};

        TypedArray array = context.obtainStyledAttributes(ATTRS);
        Drawable divider = array.getDrawable(0);
        int inset = context.getResources().getDimensionPixelSize(R.dimen.divider_decor_margin);

        InsetDrawable insetDivider = new InsetDrawable(divider, inset, 0, 0, 0);
        array.recycle();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(insetDivider);

        return itemDecoration;

    }
}
