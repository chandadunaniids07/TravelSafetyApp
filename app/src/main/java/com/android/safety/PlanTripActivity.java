package com.android.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.safety.databinding.ActivityPlanTripBinding;
import com.android.safety.locations.Feature;
import com.android.safety.locations.LocationData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlanTripActivity extends AppCompatActivity {

    private ActivityPlanTripBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanTripBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.layoutHeader.setIsMainScreen(false);
        firebaseAuth = FirebaseAuth.getInstance();
        handleClickListeners();
        sendRequest();
    }

    private void handleClickListeners() {
        binding.layoutHeader.ivBack.setOnClickListener(view -> finish());

        binding.layoutHeader.tvLogout.setOnClickListener(view -> {
            firebaseAuth.signOut();
            startActivity(new Intent(PlanTripActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void sendRequest() {
        // Authentication for the request to raspberry
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        // Sending out the request to the raspberry
        OkHttpClient okHttpClient = client.build();
        Request request = new Request.Builder()
                .url("http://api.opentripmap.com/0.1/en/places/bbox?lon_min=72.3714&lat_min=22.0225&lon_max=72.5714&lat_max=23.0225&kinds=interesting_places&format=geojson&apikey=5ae2e3f221c38a28845f05b6894039ce4dff1ecd5e754fab1007115c")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("ERR", e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBodyString = response.body().string();
                Log.e("Data", responseBodyString);
                final LocationData resp = new Gson().fromJson(responseBodyString, LocationData.class);
                final ArrayList<Feature> featureData = new ArrayList<>();
                final List<Feature> featureRespData = resp.getFeatures();
                for (int i = 0; i < featureRespData.size(); i++) {
                    final Feature feature = featureRespData.get(i);
                    if(feature.getProperties().getName().length() > 0 && feature.getGeometry().getCoordinates().size() >= 2) {
                        featureData.add(feature);
                    }
                }
                MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(featureData, PlanTripActivity.this);
                binding.setMyAdapter(myRecyclerViewAdapter);
            }
        });
    }
}