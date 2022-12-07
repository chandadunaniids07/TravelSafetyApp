package com.android.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SavedTripsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_trips);
    }

    public void goBack() {
        finish();
    }
}