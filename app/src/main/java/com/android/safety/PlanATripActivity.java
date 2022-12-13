package com.android.safety;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class PlanATripActivity extends AppCompatActivity {
    public LatLng starterMarker = new LatLng(39.9970, -75.2348);
    public LatLng destinationMarker = new LatLng(39.9955, -75.2378);
    static boolean updateMarkers = false;
    private MapsFragment mMap;
    private AutocompleteSupportFragment asc1;
    private AutocompleteSupportFragment asc2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_atrip);

        //Init and setup Places Client
        Places.initialize(getApplicationContext(), "AIzaSyB3Zbf9LcK63JP_pBFzMfUKGx9GacsBXtM");
        PlacesClient placesClient = Places.createClient(this);

        // Init and Open MapsFragment
        mMap = new MapsFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_layout,mMap).commit();


        // Init and Setup AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                starterMarker = place.getLatLng();

            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        // Init and Setup AutocompleteSupportFragment2
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment2);
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                destinationMarker = place.getLatLng();
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    public void planTripButton (View view) {
        Button button = (Button) findViewById(R.id.plan_trip_button);
        button.setOnClickListener(v -> {
            newInstance(starterMarker,destinationMarker);

        });
    }
    public MapsFragment newInstance(@NonNull LatLng sm, @NonNull LatLng dm) {
        Double starterMarkerLat = sm.latitude;
        Double starterMarkerLng = sm.longitude;
        Double destinationMarkerLat = dm.latitude;
        Double destinationMarkerLng = dm.longitude;

        Bundle args = new Bundle();
        args.putDouble("starterMarkerLat", starterMarkerLat);
        args.putDouble("starterMarkerLng", starterMarkerLng);
        args.putDouble("destinationMarkerLat",destinationMarkerLat);
        args.putDouble("destinationMarkerLng",destinationMarkerLng);

        MapsFragment mMap = new MapsFragment();
        mMap.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_layout,mMap).commit();

        return mMap;
    }
}