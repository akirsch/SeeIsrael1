package com.example.android.seeisrael.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.seeisrael.R;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FavoritesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Set App Bar title to name of project
        Toolbar myToolbar = findViewById(R.id.toolbar);
        // Set the Toolbar as Action Bar
        setSupportActionBar(myToolbar);
        // Set title of action bar to appropriate label for this Activity
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.favorite_places);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the padding to match the Status Bar height (to avoid title being cut off by
        // transparent toolbar
        myToolbar.setPadding(0, 25, 0, 0);


    }
    // When hit the back button, return to previous Activity (as this activity can be opened by more
    // than one other activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
