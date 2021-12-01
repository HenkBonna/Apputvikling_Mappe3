package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class EditHouse extends FragmentActivity {
    private GoogleMap map;
    private EditText house_id, edit_address, edit_description, edit_lat, edit_lng, edit_floors;
    private LatLng latLng;
    private String address;
    private Button save;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_house);
        map = MapsActivity.mMap;

        house_id = findViewById(R.id.house_id);
        edit_address = findViewById(R.id.edit_house_address);
        edit_description = findViewById(R.id.edit_house_description);
        edit_lat = findViewById(R.id.edit_house_coordinate_lat);
        edit_lng = findViewById(R.id.edit_house_coordinate_lng);
        edit_floors = findViewById(R.id.edit_house_floors);
        save = findViewById(R.id.save_edit);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            latLng = new LatLng(extras.getDouble("lat"), extras.getDouble("lng"));
            String lat_string = "" + latLng.latitude;
            String lng_string = "" + latLng.longitude;
            address = extras.getString("address");
            house_id.setText(extras.getInt("id"));
            edit_address.setText(address);
            edit_lat.setText(lat_string);
            edit_lng.setText(lng_string);
            edit_floors.setText(extras.getInt("floors"));
            edit_description.setText(extras.getString("description"));
        }
        edit_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_lat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_lng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_floors.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
    }

    private void checkFieldsNotEmpty() {
        if (edit_address.getText() != null
                && edit_description.getText() != null
                && edit_lat.getText() != null
                && edit_lng.getText() != null
                && edit_floors.getText() != null){
            save.setEnabled(true);
        } else {
            save.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed(){
        cancel();
    }

    public void cancel() {
        finish();
    }

    public void saveEdit(View view) {
        int id = Integer.parseInt(String.valueOf(house_id.getText()));
        Double lat = Double.parseDouble(String.valueOf(edit_lat.getText()));
        Double lng = Double.parseDouble(String.valueOf(edit_lng.getText()));
        LatLng latLong = new LatLng(lat, lng);

        String address = String.valueOf(edit_address.getText());
        String description = String.valueOf(edit_description.getText());
        int floors = Integer.parseInt(String.valueOf(edit_floors.getText()));

        //TODO: httprequest, put/update, endrehus.php
        //TODO Save this to web server
        finish();
    }
}
