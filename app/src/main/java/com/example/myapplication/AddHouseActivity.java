package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddHouseActivity extends FragmentActivity {
    private GoogleMap map;
    private EditText edit_address, edit_description, edit_lat, edit_lng, edit_floors;
    private LatLng latLng;
    private String address;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_house_activity);
        map = MapsActivity.mMap;

        edit_address = findViewById(R.id.edit_address);
        edit_description = findViewById(R.id.edit_description);
        edit_lat = findViewById(R.id.edit_coordinate_lat);
        edit_lng = findViewById(R.id.edit_coordinate_lng);
        edit_floors = findViewById(R.id.edit_floors);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            latLng = new LatLng(extras.getDouble("lat"), extras.getDouble("lng"));
            String lat_string = "" + latLng.latitude;
            String lng_string = "" + latLng.longitude;
            address = extras.getString("address");
            edit_address.setText(address);
            edit_lat.setText(lat_string);
            edit_lng.setText(lng_string);
        }
    }

    public void saveHouse(View view) {
        if (latLng != null){
            String address = String.valueOf(edit_address.getText());
            String description = String.valueOf(edit_description.getText());
            int floors = Integer.parseInt(String.valueOf(edit_floors.getText()));
            map.addMarker(new MarkerOptions().position(latLng).title(address));

            //TODO Save this to web server
            finish();
        }
    }
}
