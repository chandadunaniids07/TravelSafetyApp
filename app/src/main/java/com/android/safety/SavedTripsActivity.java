package com.android.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedTripsActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSaveList;

    ArrayList<Trips> tripsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_trips);

        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonSaveList = findViewById(R.id.button_save);

        buttonAdd.setOnClickListener(this);
        buttonSaveList.setOnClickListener(this);
    }

    public void goBack() {
        finish();
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.button_add:
                addView();
                break;
            case R.id.button_save:
                if(checkIfValidAndRead()){
                    Intent intent = new Intent(SavedTripsActivity.this, SavedTripsViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", tripsList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }

    private boolean checkIfValidAndRead() {
        tripsList.clear();
        boolean result = true;

        for (int i=0; i<layoutList.getChildCount(); i++){
            View tripsView = layoutList.getChildAt(i);

            EditText editTextStarting = (EditText)tripsView.findViewById(R.id.edit_starting_location);
            EditText editTextEnding = (EditText)tripsView.findViewById(R.id.edit_destination);

            Trips trip = new Trips();

            if(!editTextStarting.getText().toString().equals("")) {
                trip.setStartingLocation(editTextStarting.getText().toString());
            } else {
                result = false;
                break;
            }

            if(!editTextEnding.getText().toString().equals("")) {
                trip.setDestination(editTextEnding.getText().toString());
            } else {
                result = false;
                break;
            }

            tripsList.add(trip);

        }

        if(tripsList.size()==0){
            result = false;
            Toast.makeText(this,"Add Trip First!", Toast.LENGTH_SHORT).show();
        } else if (!result) {
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView(){
        View tripsView = getLayoutInflater().inflate(R.layout.row_add_saved, null, false);
        EditText editText = (EditText)tripsView.findViewById(R.id.edit_starting_location);
        EditText editText2 = (EditText)tripsView.findViewById(R.id.edit_destination);
        ImageView imageClose = (ImageView)tripsView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                removeView(tripsView);
            }
        });

        layoutList.addView(tripsView);
    }

    private void removeView(View view){
        layoutList.removeView(view);
    }
}