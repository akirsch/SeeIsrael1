package com.example.android.seeisrael.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.seeisrael.R;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {

    private Toolbar mToolbar;

    public SplashFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.splash_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // configure the toolbar
        mToolbar = getActivity().findViewById(R.id.toolbar);


        // Set the Toolbar as Action Bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        // Toolbar shouldn't display title as this is shown in textView in layout
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(R.string.app_name);
    }
}
