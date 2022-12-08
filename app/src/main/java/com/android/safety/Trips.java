package com.android.safety;

import java.io.Serializable;

public class Trips implements Serializable {

    public String startingLocation;
    public String destination;

    public Trips( ) {
    }

    public Trips(String startingLocation, String destination) {
        this.startingLocation = startingLocation;
        this.destination = destination;
    }

    public String getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
