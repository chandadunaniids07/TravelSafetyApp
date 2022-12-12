package com.android.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.safety.databinding.ActivityPlanTripBinding;
import com.android.safety.locations.Feature;
import com.android.safety.locations.LocationData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlanTripActivity extends AppCompatActivity {


    private ActivityPlanTripBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanTripBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sendRequest("http://api.opentripmap.com/0.1/en/places/bbox?lon_min=-75.2402946&lat_min=39.9953627&lon_max=-73.2402946&lat_max=42.9726154&kinds=interesting_places&format=geojson&apikey=5ae2e3f221c38a28845f05b6894039ce4dff1ecd5e754fab1007115c", new JSONObject());
    }

    private void sendRequest(String url, JSONObject json) {
        Log.d("LOG", "sendRequest: Das Json: " + json);
        // Authentication for the request to raspberry
        OkHttpClient.Builder client = new OkHttpClient.Builder();


        // Sending out the request to the raspberry
        OkHttpClient okHttpClient = client.build();

        RequestBody body = RequestBody.create(null, new byte[]{});
        if( json != null) {
            body = RequestBody.create(MediaType.parse(
                            "application/json"),
                    json.toString()
            );
        }

        Request request = new Request.Builder()
                .url(url)
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