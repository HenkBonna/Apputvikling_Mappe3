package com.example.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        this.mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view){
        House house = (House) marker.getTag();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.list_menu);
        //marker.setActionBar(toolbar);
        TextView address = view.findViewById(R.id.address),
                coordinates = view.findViewById(R.id.coordinates),
                floors = view.findViewById(R.id.floors),
                description = view.findViewById(R.id.description);
        if (house != null) {
            LatLng latLng = house.getLatLng();
            String latLong = latLng.latitude + ", " + latLng.longitude;
            address.setText(house.getAddress());
            coordinates.setText(latLong);
            String floors_amount = "" + house.getFloors();
            floors.setText(floors_amount);
            description.setText(house.getDescription());
        }
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
