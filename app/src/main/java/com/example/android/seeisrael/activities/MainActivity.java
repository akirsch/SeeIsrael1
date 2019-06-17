package com.example.android.seeisrael.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.models.Town;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Town> sTownList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set App Bar title to name of project
        Toolbar myToolbar = findViewById(R.id.toolbar);
        // Set the Toolbar as Action Bar
        setSupportActionBar(myToolbar);
        // Set title of action bar to appropriate label for this Activity
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        myToolbar.setPadding(0, 25, 0, 0);


    }
}