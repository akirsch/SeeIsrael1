package com.example.android.seeisrael.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.seeisrael.R;
import com.example.android.seeisrael.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLocationCoordinates;
    private String locationName;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        if (getIntent() != null) {
            locationName = getIntent().getStringExtra(Constants.SELECTED_PLACE_NAME_KEY);

            // get selected location co-ordinates from intent which stated this activity
            selectedLocationCoordinates = getIntent()
                    .getParcelableExtra(Constants.SELECTED_PLACE_COORINATES_KEY);

            // Set the Toolbar as Action Bar
            setSupportActionBar(mToolbar);
            // Set title of action bar to appropriate label for this Activity
            Objects.requireNonNull(getSupportActionBar()).setTitle(locationName);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } else{
            Toast.makeText(this, getString(R.string.no_maps_toast), Toast.LENGTH_LONG).show();
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in the selected location's position and move the camera
        mMap.addMarker(new MarkerOptions().position(selectedLocationCoordinates).title(getString(R.string.location_marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedLocationCoordinates));
    }
}
