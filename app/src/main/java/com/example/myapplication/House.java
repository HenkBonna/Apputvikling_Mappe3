package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class House {
    private String address, description;
    private LatLng latLng;
    private int floors, id;

    public House(int id, String description, String address, int floors, LatLng latLng) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.floors = floors;
        this.latLng = latLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }
}
