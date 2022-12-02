package com.android.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class routeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_view);
    }

    public void goBack ( ) {
        finish();
    }
}