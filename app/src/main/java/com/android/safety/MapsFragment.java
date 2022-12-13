package com.android.safety;

import static com.android.safety.PlanATripActivity.updateMarkers;

import com.android.safety.PlanATripActivity.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback{
    public LatLng starterMarker = new LatLng(39.9970, -75.2348);
    public LatLng destinationMarker = new LatLng(39.9955, -75.2378);

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_maps, container, false);
        PlanATripActivity activity = (PlanATripActivity) getActivity();
        Bundle args = getArguments();
        if (args != null) {
            starterMarker = new LatLng(args.getDouble("starterMarkerLat", 39.9970),
                    args.getDouble("starterMarkerLng", -75.2348));
            destinationMarker = new LatLng(args.getDouble("destinationMarkerLat", 39.9955),
                    args.getDouble("destinationMarkerLng", -75.2378));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

    }

    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(starterMarker));
        googleMap.addMarker(new MarkerOptions().position(destinationMarker));
    }

}
