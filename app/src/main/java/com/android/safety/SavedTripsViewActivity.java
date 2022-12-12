package com.android.safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class SavedTripsViewActivity extends AppCompatActivity {

    RecyclerView recyclerSavedTrips;
    ArrayList<Trips> tripsList = new ArrayList<>();

    TripsAdapter.OnItemClickListener listener = new TripsAdapter.OnItemClickListener() {
        @Override public void onItemClick() {
            Intent intent = new Intent(SavedTripsViewActivity.this, PlanATripActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_trips_view);

        recyclerSavedTrips = findViewById(R.id.recycler_savedTrips);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerSavedTrips.setLayoutManager(layoutManager);

        tripsList = (ArrayList<Trips>) getIntent().getExtras().getSerializable("list");

        recyclerSavedTrips.setAdapter(new TripsAdapter(tripsList, listener));

    }
}