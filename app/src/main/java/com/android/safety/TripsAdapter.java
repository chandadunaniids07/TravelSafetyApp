package com.android.safety;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripsView> {

    ArrayList<Trips> tripsList = new ArrayList<>();
    private OnItemClickListener listener;

    public TripsAdapter(ArrayList<Trips> tripsList, OnItemClickListener listener) {
        this.tripsList = tripsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TripsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trips,parent,false);

        return new TripsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripsView holder, int position){
        Trips trip = tripsList.get(position);
        holder.textStartingLocation.setText(trip.getStartingLocation());
        holder.textDestination.setText(trip.getDestination());
        holder.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public class TripsView extends RecyclerView.ViewHolder{

        TextView textStartingLocation, textDestination;
        ImageButton buttonNext;
        public TripsView(@NonNull View itemView) {
            super(itemView);

            textStartingLocation = (TextView)itemView.findViewById(R.id.text_starting_location);
            textDestination = (TextView)itemView.findViewById(R.id.text_destination);
            buttonNext = (ImageButton)itemView.findViewById(R.id.button_input);
        }

    }
}
