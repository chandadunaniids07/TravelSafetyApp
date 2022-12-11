package com.android.safety;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;

import com.android.safety.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        firebaseAuth = FirebaseAuth.getInstance();
        handleClickListeners();
        binding.layoutHeader.setIsMainScreen(true);
    }



    private void handleClickListeners() {
        binding.layoutHeader.ivBack.setOnClickListener(view -> finish());

        binding.layoutHeader.tvLogout.setOnClickListener(view -> {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        binding.btnCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8545784151"));
            startActivity(intent);
        });

        binding.btnTripWithFriends.setOnClickListener(view -> {
            try {
                String uri = "uber://?action=setPickup&pickup=my_location";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.ubercab")));
            }
        });

        binding.btnRoutes.setOnClickListener(view -> {
            // Create a Uri from an intent string. Use the result to create an Intent.
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=iconic places");

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
        });

        binding.btnShareTrip.setOnClickListener(view -> {
            String uri = "https://www.google.com/maps/dir/43.2345,-76.2345/43.12345,-76.12345" ;

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String ShareSub = "Here is my location";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });

        binding.btnCallList.setOnClickListener(view -> {
            Intent startShuttle = new Intent(this, ContactsActivity.class);
            startActivity(startShuttle);
        });

        binding.btnShuttleSchedule.setOnClickListener(view -> {
            Intent startShuttle = new Intent(this, ShuttleScheduleActivity.class);
            startActivity(startShuttle);
        });

        binding.btnSavedTrips.setOnClickListener(view -> {
            Intent startTrips = new Intent(this, SavedTripsActivity.class);
            startActivity(startTrips);
        });

        binding.btnPlanATrip.setOnClickListener(view -> {
            Intent startTrips = new Intent(this, PlanATripActivity.class);
            startActivity(startTrips);
        });

        binding.btnVirtualFriendWalk.setOnClickListener(view -> {
            Intent startTrips = new Intent(this, PlanTripActivity.class);
            startActivity(startTrips);
        });
    }
}